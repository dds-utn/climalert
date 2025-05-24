package ar.utn.ba.ddsi.mailing.models.entities.climas;

import ar.utn.ba.ddsi.mailing.models.entities.Temperatura;
import ar.utn.ba.ddsi.mailing.models.entities.lugares.Ciudad;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Clima { //responsabilidad: se la cambio, la ciudad conoce su clima, no al reves
    private Long id;
    private Ciudad ciudad;
    private Temperatura temperatura;
    private String condicion;
    private Double velocidadVientoKmh;
    private Integer humedad;
    private LocalDateTime fechaActualizacion;
    private boolean procesado;

    public Clima() {
        this.fechaActualizacion = LocalDateTime.now();
        this.procesado = false;
    }

    public boolean getProcesado(){
        return procesado;
    }

} 