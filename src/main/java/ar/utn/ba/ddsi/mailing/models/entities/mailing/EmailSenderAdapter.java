package ar.utn.ba.ddsi.mailing.models.entities.mailing;

import ar.utn.ba.ddsi.mailing.models.entities.Email;

public interface EmailSenderAdapter {
    void enviar(Email email);
}
