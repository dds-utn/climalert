package ar.utn.ba.ddsi.mailing.controllers;

import ar.utn.ba.ddsi.mailing.models.dto.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.dto.EmailOutputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.emails.Email;
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
    public EmailOutputDTO crearEmail(@RequestBody EmailInputDTO emailInputDTO) {
        return emailService.crearEmail(emailInputDTO);
    }

    @GetMapping
    public List<Email> obtenerEmails(@RequestParam(required = false) Boolean pendiente) {
        return emailService.obtenerEmails(pendiente);
    }
} 