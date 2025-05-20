package ar.utn.ba.ddsi.mailing.models.entities.Alertas;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;

public class AlertaTyH implements IAlertas {
    private int temperaturaLimiteC;
    private int humedadLimite;
    private boolean vencida;

    public AlertaTyH(int temperaturaLimiteC, int humedadLimite) {
        this.temperaturaLimiteC = temperaturaLimiteC;
        this.humedadLimite = humedadLimite;
        this.vencida = false;
    }
    @Override
    public boolean seCumpleCon(Clima clima) {
        if (vencida) return false;
        return clima.getTemperaturaCelsius() > temperaturaLimiteC &&
                clima.getHumedad() > humedadLimite;
    }

    public void marcarComoVencida() {
        this.vencida = true;
    }

    public boolean estaVencida() {
        return vencida;
    }
}

