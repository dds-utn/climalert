package ar.utn.ba.ddsi.mailing.models.entities.mailing;

import ar.utn.ba.ddsi.mailing.models.entities.Email;
import jakarta.websocket.Session;
import org.apache.logging.log4j.message.Message;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class JavaMailAdapter implements EmailSenderAdapter {

    private final String usuario;
    private final String password;

    public JavaMailAdapter(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    @Override
    public void enviar(Email email) {}
}

