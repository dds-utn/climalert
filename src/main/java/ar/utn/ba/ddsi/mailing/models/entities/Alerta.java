package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Alerta {
    private Long id;
    private String asunto = "Alerta de Clima - Condiciones Extremas";
    private String ciudad;
    private Double temperaturaCelsius;
    private Integer humedad;
    private String condicion;
    private Double velocidadVientoKmh;

    private String mensaje = String.format(
            "ALERTA: Condiciones climáticas extremas detectadas en %s\n\n" +
                    "Temperatura: %.1f°C\n" +
                    "Humedad: %d%%\n" +
                    "Condición: %s\n" +
                    "Velocidad del viento: %.1f km/h\n\n" +
                    "Se recomienda tomar precauciones.",
            this.ciudad,
            this.temperaturaCelsius,
            this.humedad,
            this.condicion,
            this.velocidadVientoKmh
    );

    public static Alerta of(String asunto, String ciudad, Double temperaturaCelsius, Integer humedad, String condicion, Double velocidadVientoKmh) {
        return Alerta.builder()
                .asunto(asunto)
                .ciudad(ciudad)
                .temperaturaCelsius(temperaturaCelsius)
                .humedad(humedad)
                .condicion(condicion)
                .velocidadVientoKmh(velocidadVientoKmh)
                .build();
    }
}
