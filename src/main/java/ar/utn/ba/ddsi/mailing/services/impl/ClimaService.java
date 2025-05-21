package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.dto.inputs.ClimaInput;
import ar.utn.ba.ddsi.mailing.models.entities.Clima;
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
    private static final String[] CIUDADES_ARGENTINA = {
            "Buenos Aires", "Cordoba", "Rosario", "Mendoza", "Tucuman",
            "La Plata", "Mar del Plata", "Salta", "Santa Fe", "San Juan"
    };

    private final IClimaRepository climaRepository;
    private final WebClient webClient;
    private final String apiKey;

    public ClimaService(
            IClimaRepository climaRepository,
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.base-url}") String baseUrl) {
        this.climaRepository = climaRepository;
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Mono<Void> actualizarClimaCiudades() {
        return Flux.fromArray(CIUDADES_ARGENTINA)
                .flatMap(this::obtenerClimaDeAPI)
                .flatMap(climaInput -> {
                    Clima clima = Clima.of(
                            climaInput.getCiudad(),
                            climaInput.getRegion(),
                            climaInput.getPais(),
                            climaInput.getTemperaturaCelsius(),
                            climaInput.getTemperaturaFahrenheit(),
                            climaInput.getCondicion(),
                            climaInput.getVelocidadVientoKmh(),
                            climaInput.getHumedad(),
                            climaInput.getFechaActualizacion());
                    climaRepository.save(clima);
                    logger.info("Clima actualizado para: {}", clima.getCiudad());
                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    logger.error("Error al actualizar el clima: {}", e.getMessage());
                    return Mono.empty();
                })
                .then();
    }

    private Mono<ClimaInput> obtenerClimaDeAPI(String ciudad) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", apiKey)
                        .queryParam("q", ciudad)
                        .queryParam("aqi", "no")
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .map(response -> {
                    return ClimaInput.of(ciudad,
                            response.getLocation().getRegion(),
                            response.getLocation().getCountry(),
                            response.getCurrent().getTemp_c(),
                            response.getCurrent().getTemp_f(),
                            response.getCurrent().getCondition().getText(),
                            response.getCurrent().getWind_kph(),
                            response.getCurrent().getHumidity());
                });
    }
} 