package nicmora.challenge.quasar.repository;

import nicmora.challenge.quasar.model.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SatelliteRepository {

    private final Map<String, Satellite> satellites;

    @Autowired
    public SatelliteRepository(List<Satellite> source) {
        this.satellites = new HashMap<>();
        source.forEach(satellite -> satellites.put(satellite.getName(), satellite));
    }

    public Flux<Satellite> findAll() {
        return Flux.fromIterable(satellites.values());
    }

    public Mono<Satellite> findByName(String name) {
        return Mono.just(satellites.get(name));
    }

    public Mono<Satellite> save(Satellite satellite) {
        return Mono.just(satellite)
                .mapNotNull(sat -> satellites.put(sat.getName(), sat))
                .thenReturn(satellite);
    }

}
