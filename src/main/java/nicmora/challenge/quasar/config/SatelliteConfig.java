package nicmora.challenge.quasar.config;

import nicmora.challenge.quasar.model.Satellite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SatelliteConfig {

    @Bean
    public Satellite getKenobi(@Value("${satellite.config.distance.kenobi:#{null}}") Float distance,
                               @Value("${satellite.config.message.kenobi:#{null}}") String[] message) {
        return Satellite.builder()
                .name("kenobi")
                .x(-500f)
                .y(-200f)
                .distance(distance)
                .message(message)
                .build();
    }

    @Bean
    public Satellite getSkywalker(@Value("${satellite.config.distance.skywalker:#{null}}") Float distance,
                                  @Value("${satellite.config.message.skywalker:#{null}}") String[] message) {
        return Satellite.builder()
                .name("skywalker")
                .x(100f)
                .y(-100f)
                .distance(distance)
                .message(message)
                .build();
    }

    @Bean
    public Satellite getSato(@Value("${satellite.config.distance.sato:#{null}}") Float distance,
                             @Value("${satellite.config.message.sato:#{null}}") String[] message) {
        return Satellite.builder()
                .name("sato")
                .x(500f)
                .y(100f)
                .distance(distance)
                .message(message)
                .build();
    }

}
