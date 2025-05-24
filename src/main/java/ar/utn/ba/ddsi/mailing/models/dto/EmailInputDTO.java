package ar.utn.ba.ddsi.mailing.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailInputDTO {
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;
}
