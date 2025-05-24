package ar.utn.ba.ddsi.mailing.models.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailOutputDTO {
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;
}
