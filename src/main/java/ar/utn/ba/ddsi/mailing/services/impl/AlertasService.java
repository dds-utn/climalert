package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.dto.inputs.EmailInput;
import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import ar.utn.ba.ddsi.mailing.services.IAlertasService;
import ar.utn.ba.ddsi.mailing.services.ICondicionAlerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@Service
public class AlertasService implements IAlertasService {
    private static final Logger logger = LoggerFactory.getLogger(AlertasService.class);
    private final ICondicionAlerta condicionAlerta;
    private final IClimaRepository climaRepository;
    private final EmailService emailService;
    private final String remitente;
    private final List<String> destinatarios;

    public AlertasService(
            IClimaRepository climaRepository,
            EmailService emailService,
            ICondicionAlerta condicionAlerta,
            @Value("${email.alertas.remitente}") String remitente,
            @Value("${email.alertas.destinatarios}") String destinatarios) {
        this.climaRepository = climaRepository;
        this.emailService = emailService;
        this.remitente = remitente;
        this.condicionAlerta = condicionAlerta;
        this.destinatarios = Arrays.asList(destinatarios.split(","));
    }

    @Override
    public Mono<Void> generarAlertasYAvisar() {
        return Mono.fromCallable(() -> climaRepository.findByProcesado(false))
                .flatMap(climas -> {
                    logger.info("Procesando {} registros de clima no procesados", climas.size());
                    return Mono.just(climas);
                })
                .flatMap(climas -> {
                    climas.stream()
                            .filter(condicionAlerta::cumpleCondicionAlerta)
                            .forEach(this::generarYEnviarEmail);

                    // Marcar todos como procesados
                    climas.forEach(clima -> {
                        clima.setProcesado(true);
                        climaRepository.save(clima);
                    });

                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    logger.error("Error al procesar alertas: {}", e.getMessage());
                    return Mono.empty();
                })
                .then();
    }

    private void generarYEnviarEmail(Clima clima) {
        Alerta alerta = Alerta.of("Alerta de Clima - Condiciones Extremas", clima.getCiudad(), clima.getTemperaturaCelsius(), clima.getHumedad(), clima.getCondicion(), clima.getVelocidadVientoKmh());

        for (String destinatario : destinatarios) {
            EmailInput emailInput = EmailInput.of(destinatario,remitente,alerta.getAsunto(),alerta.getMensaje());
            emailService.crearEmail(emailInput);
        }

        logger.info("Email de alerta generado para {} - Enviado a {} destinatarios",
                clima.getCiudad(), destinatarios.size());
    }
} 