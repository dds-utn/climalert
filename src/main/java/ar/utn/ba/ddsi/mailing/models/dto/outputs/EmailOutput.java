package ar.utn.ba.ddsi.mailing.models.dto.outputs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailOutput {
    private Long id;
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;

    public static EmailOutput of(Long id, String destinatario, String remitente, String asunto, String contenido) {
        return EmailOutput.builder().id(id).destinatario(destinatario).remitente(remitente).asunto(asunto).contenido(contenido).build();
    }
}
