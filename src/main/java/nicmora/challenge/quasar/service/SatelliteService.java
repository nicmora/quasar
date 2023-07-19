package nicmora.challenge.quasar.service;

import nicmora.challenge.quasar.model.Satellite;
import nicmora.challenge.quasar.repository.SatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SatelliteService {

    private final SatelliteRepository repository;

    @Autowired
    public SatelliteService(SatelliteRepository repository) {
        this.repository = repository;
    }

    public Flux<Satellite> getAll() {
        return repository.findAll();
    }

    public Mono<Satellite> getByName(String name) {
        return repository.findByName(name);
    }

    public Mono<Satellite> updateDistanceMessageByName(String name, Float distance, String[] message) {
        return repository.findByName(name)
                .map(satellite -> {
                    satellite.setDistance(distance);
                    satellite.setMessage(message);
                    return satellite;
                })
                .flatMap(repository::save);
    }

}
