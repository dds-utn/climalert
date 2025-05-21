package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Email {
    private Long id;
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;
    private boolean enviado;

    public static Email of(String destinatario, String remitente, String asunto, String contenido){
        return Email.builder().destinatario(destinatario).remitente(remitente).asunto(asunto).contenido(contenido).enviado(false).build();
    }

    public void enviar() {
        //TODO: Implementación pendiente. Podríamos usar adapters
    }
} 