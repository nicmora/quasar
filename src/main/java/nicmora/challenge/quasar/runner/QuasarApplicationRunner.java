package nicmora.challenge.quasar.runner;

import lombok.extern.slf4j.Slf4j;
import nicmora.challenge.quasar.model.Satellite;
import nicmora.challenge.quasar.service.QuasarLocationService;
import nicmora.challenge.quasar.service.QuasarMessageService;
import nicmora.challenge.quasar.service.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
//@Component
public class QuasarApplicationRunner implements ApplicationRunner {

    private final SatelliteService satelliteService;
    private final QuasarLocationService locationService;
    private final QuasarMessageService messageService;

//    @Autowired
    public QuasarApplicationRunner(SatelliteService satelliteService,
                                   QuasarLocationService locationService,
                                   QuasarMessageService messageService) {
        this.satelliteService = satelliteService;
        this.locationService = locationService;
        this.messageService = messageService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux<Satellite> satellites = satelliteService.getAll();

        satellites.collectList()
                .map(s -> s.toArray(Satellite[]::new))
                .map(locationService::getLocation)
                .doOnNext(position -> log.info("Location (x={} ; y={})", position.get("x"), position.get("y")))
                .subscribe();

        satellites.collectList()
                .map(s -> s.toArray(Satellite[]::new))
                .map(messageService::getMessage)
                .doOnNext(message ->  log.info("Message: {}", message))
                .subscribe();
    }

}
