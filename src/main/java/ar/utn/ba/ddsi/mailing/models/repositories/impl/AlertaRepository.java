package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.alerts.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AlertaRepository implements IAlertaRepository {
    List<Alerta> alertas;
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public void save(Alerta alerta) {
        if (alerta.getId() == null) {
            Long id = idGenerator.getAndIncrement();
            alerta.setId(id);
            alertas.add(alerta);
        }//si ya esta en el repo listo, ya esta
    }

    @Override
    public List<Alerta> findByProcesado(boolean procesado) {
        return alertas.stream()
                .filter(a -> a.isProcesado() == procesado)
                .toList();
    }
}
