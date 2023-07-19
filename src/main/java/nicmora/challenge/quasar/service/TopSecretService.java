package nicmora.challenge.quasar.service;

import lombok.extern.slf4j.Slf4j;
import nicmora.challenge.quasar.dto.request.SatelliteDTO;
import nicmora.challenge.quasar.dto.response.TopSecretResponseDTO;
import nicmora.challenge.quasar.model.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@Slf4j
public class TopSecretService {

    private final SatelliteService satelliteService;
    private final QuasarLocationService locationService;
    private final QuasarMessageService messageService;

    @Autowired
    public TopSecretService(SatelliteService satelliteService,
                            QuasarLocationService locationService,
                            QuasarMessageService messageService) {
        this.satelliteService = satelliteService;
        this.locationService = locationService;
        this.messageService = messageService;
    }

    public Mono<TopSecretResponseDTO> getTopSecret(List<SatelliteDTO> satellitesDTO) {
        return Flux.fromIterable(satellitesDTO)
                .flatMap(dto -> satelliteService.getByName(dto.getName())
                        .map(satellite -> {
                            satellite.setDistance(dto.getDistance());
                            satellite.setMessage(dto.getMessage());
                            return satellite;
                        }))
                .collectList()
                .map(satellites -> satellites.toArray(Satellite[]::new))
                .flatMap(this::getLocationMessage)
                .onErrorResume(e -> Mono.empty());
    }

    public Mono<TopSecretResponseDTO> getTopSecretSplit() {
        return satelliteService.getAll()
                .collectList()
                .map(satellites -> satellites.toArray(Satellite[]::new))
                .flatMap(this::getLocationMessage)
                .onErrorResume(e -> Mono.empty());
    }

    public Mono<Void> updateSatellite(String name, SatelliteDTO request) {
        return satelliteService.updateDistanceMessageByName(name, request.getDistance(), request.getMessage())
                .then();
    }

    private Mono<TopSecretResponseDTO> getLocationMessage(Satellite[] satellites) {
        return Mono.just(locationService.getLocation(satellites))
                .zipWith(Mono.just(messageService.getMessage(satellites)))
                .filter(tuple -> validLocation.test(tuple.getT1()))
                .filter(tuple -> validMessage.test(tuple.getT2()))
                .map(tuple -> this.buildTopSecretResponse(tuple.getT1(), tuple.getT2()));
    }

    private TopSecretResponseDTO buildTopSecretResponse(Map<String, Float> location, String message) {
        return TopSecretResponseDTO.builder()
                .location(location)
                .message(message)
                .build();
    }

    private final Predicate<Map<String, Float>> validLocation = location -> !location.get("x").isInfinite() && !location.get("y").isInfinite();
    private final Predicate<String> validMessage = message -> !message.isBlank();

}
