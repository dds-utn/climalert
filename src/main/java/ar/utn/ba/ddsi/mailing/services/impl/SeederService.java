package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.lugares.Ciudad;
import ar.utn.ba.ddsi.mailing.models.entities.lugares.Pais;
import ar.utn.ba.ddsi.mailing.models.entities.lugares.Region;
import ar.utn.ba.ddsi.mailing.models.repositories.ICiudadRepository;
import ar.utn.ba.ddsi.mailing.services.ISeederService;

public class SeederService implements ISeederService {

    ICiudadRepository ciudadRepository;

    private static Pais generarPais(String nombre){
        return new Pais(nombre);
    };

    private Region generarRegion(String nombre, Pais pais){
        return new Region(nombre,pais);
    }

    private Ciudad generarCiudad(String nombre, Region region){
        return new Ciudad(nombre,region);
    }

    public void inicializar(){
        Pais argentina = generarPais("Argentina");
        Region buenosAires = generarRegion("Buenos Aires", argentina);

        Ciudad laPlata = generarCiudad("La Plata", buenosAires);
        Ciudad capitalFederal = generarCiudad("Capital Federal", buenosAires);
        Ciudad marDelPLata = generarCiudad("Mar Del PLata", buenosAires);

        ciudadRepository.save(laPlata);
        ciudadRepository.save(capitalFederal);
        ciudadRepository.save(marDelPLata);

    }
}
