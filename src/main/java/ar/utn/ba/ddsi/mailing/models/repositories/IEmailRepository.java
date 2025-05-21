package ar.utn.ba.ddsi.mailing.models.repositories;

import ar.utn.ba.ddsi.mailing.models.entities.Email;
import java.util.List;
import java.util.Optional;

public interface IEmailRepository {
    void save(Email email);
    List<Email> findAll();
    List<Email> findByEnviado(boolean enviado);
    Optional<Email> findById(Long id);
    void delete(Email email);
} 