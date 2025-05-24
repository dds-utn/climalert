package ar.utn.ba.ddsi.mailing.schedulers;

import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private final IEmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);
    public EmailScheduler(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "${cron.expression}")
    public void procesarEmailsPendientes() {
        emailService.procesarPendientes();
    }

    @Scheduled(cron = "${cron.expression}")
    public void revisarAlertasYEnviarEmails() {
        emailService.revisarAlertasYEnviarEmails()
                .doOnSuccess(v -> logger.info("Enviado de emails completado"))
                .doOnError(e -> logger.error("Error en el enviado de emails: {}", e.getMessage()))
                .subscribe();
    }

    @Scheduled(cron = "${cron.expression}")
    public void loguearEmailsPendientes() {
        emailService.loguearEmailsPendientes();
    }


} 