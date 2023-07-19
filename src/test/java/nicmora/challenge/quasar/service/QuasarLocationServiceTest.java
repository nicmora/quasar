package nicmora.challenge.quasar.service;

import nicmora.challenge.quasar.exception.LocationException;
import nicmora.challenge.quasar.model.Satellite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest(classes = {QuasarLocationService.class})
class QuasarLocationServiceTest {

    @Autowired
    QuasarLocationService locationService;

    Satellite[] satellites;

    @BeforeEach
    void setUp(@Value("${satellite.config.distance.kenobi:#{null}}") Float kenobiDistance,
               @Value("${satellite.config.message.kenobi:#{null}}") String[] kenobiMessage,
               @Value("${satellite.config.distance.skywalker:#{null}}") Float skywalkerDistance,
               @Value("${satellite.config.message.skywalker:#{null}}") String[] skywalkerMessage,
               @Value("${satellite.config.distance.sato:#{null}}") Float satoDistance,
               @Value("${satellite.config.message.sato:#{null}}") String[] satoMessage) {
        Satellite kenobi = Satellite.builder()
                .name("kenobi")
                .x(-500f)
                .y(-200f)
                .distance(kenobiDistance)
                .build();

        Satellite skywalker = Satellite.builder()
                .name("skywalker")
                .x(100f)
                .y(-100f)
                .distance(skywalkerDistance)
                .build();

        Satellite sato = Satellite.builder()
                .name("sato")
                .x(500f)
                .y(100f)
                .distance(satoDistance)
                .build();

        satellites = new Satellite[] { kenobi, skywalker, sato };
    }

    @Test
    void getLocation() {
        Map<String, Float> position = locationService.getLocation(satellites);

        Assertions.assertEquals(-487.28592f, position.get("x"));
        Assertions.assertEquals(1557.0143f, position.get("y"));
    }

    @Test
    void whenDistanceIsNull_thenLocationException() {
        Arrays.stream(satellites).forEach(s -> s.setDistance(null));

        Assertions.assertThrows(LocationException.class, () -> locationService.getLocation(satellites));
    }
}
