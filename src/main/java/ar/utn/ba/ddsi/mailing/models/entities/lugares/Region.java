package ar.utn.ba.ddsi.mailing.models.entities.lugares;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Region {
    String nombre;
    Pais pais;
}
