package ar.utn.ba.ddsi.mailing.models.repositories;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;

import java.util.List;

public interface IAlertaRepository {
    void save(Alerta alerta);
    List<Alerta> findall();
    Alerta findById(Long id);
    void delete(Alerta alerta);
}
