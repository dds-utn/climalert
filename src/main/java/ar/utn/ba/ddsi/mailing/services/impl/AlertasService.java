package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.alerts.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.emails.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import ar.utn.ba.ddsi.mailing.models.repositories.ICiudadRepository;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import ar.utn.ba.ddsi.mailing.models.repositories.impl.AlertaRepository;
import ar.utn.ba.ddsi.mailing.services.IAlertasService;
import ar.utn.ba.ddsi.mailing.utils.IAlertDetecter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@Service
public class AlertasService implements IAlertasService {
    private static final Logger logger = LoggerFactory.getLogger(AlertasService.class);

    private final IClimaRepository climaRepository;
    private final ICiudadRepository ciudadRepository;
    private final IAlertDetecter alertDetecter;
    private final IAlertaRepository alertaRepository;

    public AlertasService(
            IClimaRepository climaRepository,
            ICiudadRepository ciudadRepository,
            IAlertDetecter alertDetecter, IAlertaRepository alertaRepository) {
        this.climaRepository = climaRepository;
        this.ciudadRepository = ciudadRepository;
        this.alertDetecter = alertDetecter;
        this.alertaRepository = alertaRepository;
    }

    @Override
    public Mono<Void> generarAlertasYGuardar() {
        return Mono.fromCallable(ciudadRepository::findAll)
            .flatMap(ciudades -> {
                logger.info("Procesando climas de ciudades");
                return Mono.just(ciudades);
            })
            .flatMap(ciudades -> {
                ciudades.forEach(ciudad -> {
                    Clima climaMasReciente = ciudad.obtenerClimaMasReciente();
                    if(!climaMasReciente.getProcesado()  && alertDetecter.cumpleCondicionesAlerta(climaMasReciente)) {
                        Alerta alerta = this.generarAlerta(climaMasReciente);
                        alertaRepository.save(alerta);
                        climaMasReciente.setProcesado(true);
                        climaRepository.save(climaMasReciente);
                    }
                });
                return Mono.empty();
            })
            .onErrorResume(e -> {
                logger.error("Error al procesar alertas: {}", e.getMessage());
                return Mono.empty();
            })
            .then();
    }

    private Alerta generarAlerta(Clima clima) {
        Alerta alerta = new Alerta();
        alerta.setCiudad(clima.getCiudad());
        alerta.setTemperatura(clima.getTemperatura());
        alerta.setCondicion(clima.getCondicion());
        alerta.setVelocidadVientoKmh(clima.getVelocidadVientoKmh());
        alerta.setHumedad(clima.getHumedad());
        return alerta;
    }


} 