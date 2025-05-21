package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AlertaRepository implements IAlertaRepository {
    private final Map<Long, Alerta> alertas = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public void save(Alerta alerta){
        if(alerta.getId() == null){
            Long id = idGenerator.getAndIncrement();
            alerta.setId(id);
            alertas.put(id, alerta);
        }else{
            alertas.put(alerta.getId(), alerta);
        }
    }

    @Override
    public List<Alerta> findall(){return new ArrayList<>(alertas.values());}

    @Override
    public Alerta findById(Long id){
        return alertas.values().stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public void delete(Alerta alerta){
        if (alerta.getId() != null) {
            alertas.remove(alerta.getId());
        }}
}