package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Temperatura {
    private double temperaturaCelsius; //keep it simple

    private double getTemperaturaFahrenheit(){
        return (temperaturaCelsius * 9 / 5) + 32;
    }

}
