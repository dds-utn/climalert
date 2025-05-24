package ar.utn.ba.ddsi.mailing.models.repositories;

import ar.utn.ba.ddsi.mailing.models.entities.lugares.Ciudad;

import java.util.List;

public interface ICiudadRepository {
    public List<Ciudad> findAll();
    public void save(Ciudad ciudad);
}
