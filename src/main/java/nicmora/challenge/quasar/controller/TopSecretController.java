package nicmora.challenge.quasar.controller;

import lombok.extern.slf4j.Slf4j;
import nicmora.challenge.quasar.dto.request.SatelliteDTO;
import nicmora.challenge.quasar.dto.request.TopSecretRequestDTO;
import nicmora.challenge.quasar.dto.response.TopSecretResponseDTO;
import nicmora.challenge.quasar.service.TopSecretService;
import nicmora.challenge.quasar.utils.JSONHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TopSecretController {

    private final String KEY = this.getClass().getSimpleName() + " ->";

    private final TopSecretService topSecretService;

    @Autowired
    public TopSecretController(TopSecretService topSecretService) {
        this.topSecretService = topSecretService;
    }

    @PostMapping("/topsecret")
    public Mono<ResponseEntity<TopSecretResponseDTO>> getTopSecret(@RequestBody TopSecretRequestDTO request) {
        log.info("{} POST topsecret with body: {}", KEY, JSONHandler.getInstance().toJson(request));

        return topSecretService.getTopSecret(request.getSatellites())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/topsecret_split/{satellite_name}")
    public Mono<Void> updateSatellite(@PathVariable("satellite_name") String name,
                                      @RequestBody SatelliteDTO request) {
        log.info("{} POST topsecret_split with satellite_name: {} and body: {}", KEY, name, JSONHandler.getInstance().toJson(request));

        return topSecretService.updateSatellite(name, request);
    }

    @GetMapping("/topsecret_split")
    public Mono<ResponseEntity<TopSecretResponseDTO>> getTopSecretSplit() {
        log.info("{} GET topsecret_split", KEY);

        return topSecretService.getTopSecretSplit()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}
