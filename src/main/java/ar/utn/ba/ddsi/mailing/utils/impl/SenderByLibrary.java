package ar.utn.ba.ddsi.mailing.utils.impl;

import ar.utn.ba.ddsi.mailing.models.entities.emails.Email;
import ar.utn.ba.ddsi.mailing.utils.IEmailSenderAdapter;
import org.springframework.stereotype.Component;

@Component
public class SenderByLibrary implements IEmailSenderAdapter {
    @Override
    public void enviar(Email email) {
        //todo
    }
}
