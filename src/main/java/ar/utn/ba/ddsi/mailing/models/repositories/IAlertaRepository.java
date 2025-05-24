package ar.utn.ba.ddsi.mailing.models.repositories;

import ar.utn.ba.ddsi.mailing.models.entities.alerts.Alerta;

import java.util.List;

public interface IAlertaRepository {
     public void save(Alerta alerta);
     public List<Alerta> findByProcesado(boolean procesado);
}
