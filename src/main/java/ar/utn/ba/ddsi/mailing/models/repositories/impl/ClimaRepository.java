package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ClimaRepository implements IClimaRepository {
    private final Map<Long, Clima> climas = new HashMap<>();
    private final Map<String, Long> ciudadToId = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public void save(Clima clima) {
        if (clima.getId() == null) {
            Long id = idGenerator.getAndIncrement();
            clima.setId(id);
            climas.put(id, clima);
            ciudadToId.put(clima.getCiudad(), id);
        } else {
            climas.put(clima.getId(), clima);
            ciudadToId.put(clima.getCiudad(), clima.getId());
        }
    }

    @Override
    public List<Clima> findAll() {
        return new ArrayList<>(climas.values());
    }

    @Override
    public Optional<Clima> findById(Long id) {
        return Optional.ofNullable(climas.get(id));
    }

    @Override
    public Optional<Clima> findByCiudad(String ciudad) {
        Long id = ciudadToId.get(ciudad);
        return id != null ? Optional.of(climas.get(id)) : Optional.empty();
    }

    @Override
    public List<Clima> findByProcesado(boolean procesado) {
        return climas.values().stream()
                .filter(c -> c.isProcesado() == procesado)
                .toList();
    }

    @Override
    public void delete(Clima clima) {
        if (clima.getId() != null) {
            climas.remove(clima.getId());
            ciudadToId.remove(clima.getCiudad());
        }
    }
} 