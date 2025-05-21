package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.services.ICondicionAlerta;
import org.springframework.stereotype.Component;

@Component
public class CondicionAlertaTempYHumedad implements ICondicionAlerta {
    private static final double TEMPERATURA_MAXIMA = 35.0;
    private static final int HUMEDAD_MAXIMA = 60;

    @Override
    public boolean cumpleCondicionAlerta(Clima clima){
        return clima.getTemperaturaCelsius() > TEMPERATURA_MAXIMA &&
                clima.getHumedad() > HUMEDAD_MAXIMA;
    }
}
