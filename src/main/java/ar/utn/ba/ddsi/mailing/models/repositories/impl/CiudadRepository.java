package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.lugares.Ciudad;
import ar.utn.ba.ddsi.mailing.models.repositories.ICiudadRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CiudadRepository implements ICiudadRepository {
    private final List<Ciudad> ciudades;

    public CiudadRepository() {
        ciudades = new ArrayList<>();
    }
    @Override
    public List<Ciudad> findAll() {
        return ciudades;
    }

    @Override
    public void save(Ciudad ciudad) {
        ciudades.add(ciudad);
    }
}
