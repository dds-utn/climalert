package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.climas.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.Temperatura;
import ar.utn.ba.ddsi.mailing.models.entities.lugares.Ciudad;
import ar.utn.ba.ddsi.mailing.models.repositories.ICiudadRepository;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import ar.utn.ba.ddsi.mailing.models.dto.external.weatherapi.WeatherResponse;
import ar.utn.ba.ddsi.mailing.services.IClimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClimaService implements IClimaService {
    private static final Logger logger = LoggerFactory.getLogger(ClimaService.class);

    private final ICiudadRepository ciudadRepository;
    private final IClimaRepository climaRepository;
    private final WebClient webClient;
    private final String apiKey;

    public ClimaService(
            IClimaRepository climaRepository,
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.base-url}") String baseUrl,
            ICiudadRepository ciudadRepository) {
        this.climaRepository = climaRepository;
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
        this.ciudadRepository = ciudadRepository;
    }

    @Override
    public Mono<Void> actualizarClimaCiudades() {
        return Flux.fromStream(ciudadRepository.findAll().stream())
            .flatMap(this::obtenerClimaDeAPI)
            .flatMap(clima -> {
                climaRepository.save(clima);
                logger.info("Clima actualizado para: {}", clima.getCiudad().getNombre());
                return Mono.empty();
            })
            .onErrorResume(e -> {
                logger.error("Error al actualizar el clima: {}", e.getMessage());
                return Mono.empty();
            })
            .then();
    }

    private Mono<Clima> obtenerClimaDeAPI(Ciudad ciudad) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/current.json")
                .queryParam("key", apiKey)
                .queryParam("q", ciudad.getNombre())
                .queryParam("aqi", "no")
                .build())
            .retrieve()
            .bodyToMono(WeatherResponse.class)
            .map(response -> {
                Clima clima = new Clima();
                clima.setCiudad(ciudad);
                clima.setTemperatura(new Temperatura(response.getCurrent().getTemp_c()));
                clima.setCondicion(response.getCurrent().getCondition().getText());
                clima.setVelocidadVientoKmh(response.getCurrent().getWind_kph());
                clima.setHumedad(response.getCurrent().getHumidity());
                ciudad.agregarClima(clima);
                return clima;
            });
    }

}