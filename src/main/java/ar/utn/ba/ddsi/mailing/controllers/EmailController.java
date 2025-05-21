package ar.utn.ba.ddsi.mailing.controllers;

import ar.utn.ba.ddsi.mailing.models.dto.inputs.EmailInput;
import ar.utn.ba.ddsi.mailing.models.dto.outputs.EmailOutput;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public EmailOutput crearEmail(@RequestBody EmailInput emailInput) {
        return emailService.crearEmail(emailInput);
    }

    @GetMapping
    public List<EmailOutput> obtenerEmails(@RequestParam(required = false) Boolean pendiente) {
        return emailService.obtenerEmails(pendiente);
    }
}