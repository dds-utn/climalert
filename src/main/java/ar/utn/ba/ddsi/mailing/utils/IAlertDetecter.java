package ar.utn.ba.ddsi.mailing.utils;

import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;

public interface IAlertDetecter {

    boolean cumpleCondicionesAlerta(Clima clima);
}
