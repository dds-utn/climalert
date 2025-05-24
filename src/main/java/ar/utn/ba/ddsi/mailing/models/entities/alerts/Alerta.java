package ar.utn.ba.ddsi.mailing.models.entities.alerts;

import ar.utn.ba.ddsi.mailing.models.entities.Temperatura;
import ar.utn.ba.ddsi.mailing.models.entities.lugares.Ciudad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Alerta {
    private Long id;
    private Ciudad ciudad;
    private Temperatura temperatura;
    private Integer humedad;
    private String condicion;
    private Double velocidadVientoKmh;
    boolean procesado = false;
}
