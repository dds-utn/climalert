package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.dto.inputs.EmailInput;
import ar.utn.ba.ddsi.mailing.models.dto.outputs.EmailOutput;
import java.util.List;

public interface IEmailService {
    EmailOutput crearEmail(EmailInput emailInput);
    List<EmailOutput> obtenerEmails(Boolean pendiente);
    void procesarPendientes();
    void loguearEmailsPendientes();
} 