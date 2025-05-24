package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.dto.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.dto.EmailOutputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.alerts.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.emails.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import ar.utn.ba.ddsi.mailing.models.repositories.IEmailRepository;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import ar.utn.ba.ddsi.mailing.utils.IEmailSenderAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailService implements IEmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final IEmailRepository emailRepository;
    private final String remitente;
    private final List<String> destinatarios;
    private final IEmailSenderAdapter sender;
    private final IAlertaRepository alertaRepository;
    public EmailService(IEmailRepository emailRepository,
                        EmailService emailService,
                        @Value("${email.alertas.remitente}") String remitente,
                        @Value("${email.alertas.destinatarios}") String destinatarios, IEmailSenderAdapter sender, IAlertaRepository alertaRepository) {
        this.remitente = remitente;
        this.destinatarios = Arrays.asList(destinatarios.split(","));
        this.emailRepository = emailRepository;
        this.sender = sender;
        this.alertaRepository = alertaRepository;
    }

    @Override
    public EmailOutputDTO crearEmail(EmailInputDTO emailInputDTO) {
        Email email = emailRepository.save(new Email(emailInputDTO.getDestinatario(),emailInputDTO.getRemitente(),emailInputDTO.getAsunto(), emailInputDTO.getContenido()));
        return new EmailOutputDTO(email.getDestinatario(),email.getRemitente(),email.getAsunto(),email.getContenido());
    }

    @Override
    public List<Email> obtenerEmails(Boolean pendiente) {
        if (pendiente != null) {
            return emailRepository.findByEnviado(!pendiente);
        }
        return emailRepository.findAll();
    }

    @Override
    public void procesarPendientes() {
        List<Email> pendientes = emailRepository.findByEnviado(false);
        for (Email email : pendientes) {
            sender.enviar(email);
            email.setEnviado(true);
            emailRepository.save(email);
        }
    }

    @Override
    public void loguearEmailsPendientes() {
        List<Email> pendientes = obtenerEmails(true);
        logger.info("Emails pendientes de envío: {}", pendientes.size());
        pendientes.forEach(email -> 
            logger.info("Email pendiente - ID: {}, Destinatario: {}, Asunto: {}", 
                email.getId(),
                email.getDestinatario(), 
                email.getAsunto())
        );
    }

    private void generarYGuardarEmail(Alerta alerta) {
        String asunto = "Alerta de Clima - Condiciones Extremas";
        String mensaje = String.format(
                "ALERTA: Condiciones climáticas extremas detectadas en %s\n\n" +
                        "Temperatura: %.1f°C\n" +
                        "Humedad: %d%%\n" +
                        "Condición: %s\n" +
                        "Velocidad del viento: %.1f km/h\n\n" +
                        "Se recomienda tomar precauciones.",
                alerta.getCiudad(),
                alerta.getTemperatura().getTemperaturaCelsius(),
                alerta.getHumedad(),
                alerta.getCondicion(),
                alerta.getVelocidadVientoKmh()
        );

        for (String destinatario : destinatarios) {
            EmailInputDTO emailInputDTO = new EmailInputDTO(destinatario, remitente, asunto, mensaje);
            this.crearEmail(emailInputDTO);
        }

        logger.info("Email de alerta generado para {} - Enviado a {} destinatarios",
                alerta.getCiudad().getNombre(), destinatarios.size());
    }
    @Override
    public Mono<Void> revisarAlertasYEnviarEmails() {
        return Mono.fromCallable(() -> alertaRepository.findByProcesado(false))
                .flatMap(alertas -> {
                    logger.info("Procesando {} registros de alertas no procesadas", alertas.size());
                    return Mono.just(alertas);
                })
                .flatMap(alertas -> {
                    alertas.forEach(this::generarYGuardarEmail);

                    alertas.forEach(alerta -> {
                        alerta.setProcesado(true);
                        alertaRepository.save(alerta);
                    });

                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    logger.error("Error al procesar alertas: {}", e.getMessage());
                    return Mono.empty();
                })
                .then();
    }
} 