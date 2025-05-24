package ar.utn.ba.ddsi.mailing.utils;

import ar.utn.ba.ddsi.mailing.models.entities.emails.Email;

public interface IEmailSenderAdapter {
    void enviar(Email email);
}
