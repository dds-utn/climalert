package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.dto.inputs.EmailInput;
import ar.utn.ba.ddsi.mailing.models.dto.outputs.EmailOutput;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
//import ar.utn.ba.ddsi.mailing.models.exceptions.EmailNoEnviadoException;
//no se porque no me reconoce a EmailNoEnviadoException, tipo lo importa bien y aún así no lo reconoce
import ar.utn.ba.ddsi.mailing.models.repositories.IEmailRepository;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService implements IEmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final IEmailRepository emailRepository;

    public EmailService(IEmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public EmailOutput crearEmail(EmailInput emailInput) {
        Email email = Email.of(emailInput.getDestinatario(), emailInput.getRemitente(),emailInput.getAsunto(),emailInput.getContenido());
        emailRepository.save(email);
        return EmailOutput.of(email.getId(),email.getDestinatario(),email.getRemitente(),email.getAsunto(),email.getContenido());
    }

    @Override
    public List<EmailOutput> obtenerEmails(Boolean pendiente) {
        List<Email> listaDeEmails;
        if (pendiente != null) {
            listaDeEmails = emailRepository.findByEnviado(!pendiente);

        }else{
            listaDeEmails = emailRepository.findAll();
        }
        return listaDeEmails.stream()
                .map(e -> EmailOutput.of(e.getId(),e.getDestinatario(),e.getRemitente(),e.getAsunto(),e.getContenido()))
                .collect(Collectors.toList());
    }

    @Override
    public void procesarPendientes() {
        List<Email> pendientes = emailRepository.findByEnviado(false);
        for (Email email : pendientes) {
            try {
                email.enviar();
                email.setEnviado(true);
                emailRepository.save(email);
            } catch (Exception e) {
                //throw new EmailNoEnviadoException("El email con el id " + email.getId() + "no pudo ser enviado.");
                //no se porque no me reconoce a EmailNoEnviadoException, tipo lo importa bien y aún así no lo reconoce
                throw new RuntimeException("El email con el id " + email.getId() + "no pudo ser enviado.");
            }
        }
    }

    @Override
    public void loguearEmailsPendientes() {
        List<EmailOutput> pendientes = obtenerEmails(true);
        logger.info("Emails pendientes de envío: {}", pendientes.size());
        pendientes.forEach(email ->
                logger.info("Email pendiente - ID: {}, Destinatario: {}, Asunto: {}",
                        email.getId(),
                        email.getDestinatario(),
                        email.getAsunto())
        );
    }
}