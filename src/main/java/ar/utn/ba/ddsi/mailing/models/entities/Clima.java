package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clima {
    private Long id;
    private String ciudad;
    private String region;
    private String pais;
    private Double temperaturaCelsius;
    private Double temperaturaFahrenheit;
    private String condicion;
    private Double velocidadVientoKmh;
    private Integer humedad;
    private LocalDateTime fechaActualizacion;
    private boolean procesado;

    public static Clima of(String ciudad, String region, String pais, Double temperaturaCelsius, Double temperaturaFahrenheit, String coindicion, Double velocidadVientoKmh, Integer humedad, LocalDateTime fechaActualizacion) {
        return Clima
                .builder()
                .ciudad(ciudad)
                .region(region)
                .pais(pais)
                .temperaturaCelsius(temperaturaCelsius)
                .temperaturaFahrenheit(temperaturaFahrenheit)
                .condicion(coindicion)
                .velocidadVientoKmh(velocidadVientoKmh)
                .humedad(humedad)
                .fechaActualizacion(fechaActualizacion)
                .procesado(false)
                .build();
    }
}