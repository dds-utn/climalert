package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.dto.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.dto.EmailOutputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.emails.Email;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IEmailService {
    EmailOutputDTO crearEmail(EmailInputDTO emailInputDTO);
    List<Email> obtenerEmails(Boolean pendiente);
    void procesarPendientes();
    public Mono<Void> revisarAlertasYEnviarEmails();
    void loguearEmailsPendientes();
} 