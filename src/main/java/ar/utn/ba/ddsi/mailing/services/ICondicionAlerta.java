package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;

public interface ICondicionAlerta {
    public boolean cumpleCondicionAlerta(Clima clima);
}
