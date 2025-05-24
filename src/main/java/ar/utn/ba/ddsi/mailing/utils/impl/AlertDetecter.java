package ar.utn.ba.ddsi.mailing.utils.impl;

import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;
import ar.utn.ba.ddsi.mailing.utils.IAlertDetecter;
import org.springframework.stereotype.Component;

@Component
public class AlertDetecter implements IAlertDetecter {

    private  final double TEMPERATURA_ALERTA = 35.0;
    private  final int HUMEDAD_ALERTA = 60;

    public boolean cumpleCondicionesAlerta(Clima clima) {
        return clima.getTemperatura().getTemperaturaCelsius() > TEMPERATURA_ALERTA &&
                clima.getHumedad() > HUMEDAD_ALERTA;
    }
}
