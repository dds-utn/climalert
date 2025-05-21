package ar.utn.ba.ddsi.mailing.models.dto.inputs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailInput {
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;

    public static EmailInput of(String destinatario, String remitente, String asunto, String contenido) {
        return EmailInput.builder().destinatario(destinatario).remitente(remitente).asunto(asunto).contenido(contenido).build();
    }
}