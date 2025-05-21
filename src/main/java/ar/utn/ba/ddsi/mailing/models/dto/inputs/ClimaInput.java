package ar.utn.ba.ddsi.mailing.models.dto.inputs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClimaInput {
    private String ciudad;
    private String region;
    private String pais;
    private Double temperaturaCelsius;
    private Double temperaturaFahrenheit;
    private String condicion;
    private Double velocidadVientoKmh;
    private Integer humedad;
    private LocalDateTime fechaActualizacion;

    public static ClimaInput of(String ciudad, String region, String pais, Double temperaturaCelsius, Double temperaturaFahrenheit, String coindicion, Double velocidadVientoKmh, Integer humedad) {
        return ClimaInput
                .builder()
                .ciudad(ciudad)
                .region(region)
                .pais(pais)
                .temperaturaCelsius(temperaturaCelsius)
                .temperaturaFahrenheit(temperaturaFahrenheit)
                .condicion(coindicion)
                .velocidadVientoKmh(velocidadVientoKmh)
                .humedad(humedad)
                .fechaActualizacion(LocalDateTime.now())
                .build();
    }
}