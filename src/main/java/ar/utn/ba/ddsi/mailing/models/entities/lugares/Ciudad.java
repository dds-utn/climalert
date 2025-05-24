package ar.utn.ba.ddsi.mailing.models.entities.lugares;

import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Setter
@Getter
public class Ciudad {
    String nombre;
    Region region;
    List<Clima> climas;
    public Ciudad(String nombre,Region region) {
        this.nombre = nombre;
        this.region = region;
        climas = new ArrayList<>();
    }

    public void agregarClima(Clima clima){
        this.climas.add(clima);
    }

    public Clima obtenerClimaMasReciente(){
        return climas.stream().max(Comparator.comparing(Clima::getFechaActualizacion)).orElse(null);
    }
}
