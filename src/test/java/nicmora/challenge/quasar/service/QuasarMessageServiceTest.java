package nicmora.challenge.quasar.service;

import nicmora.challenge.quasar.exception.MessageException;
import nicmora.challenge.quasar.model.Satellite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = {QuasarMessageService.class})
class QuasarMessageServiceTest {

    @Autowired
    QuasarMessageService messageService;

    Satellite[] satellites;

    @BeforeEach
    void setUp(@Value("${satellite.config.message.kenobi:#{null}}") String[] kenobiMessage,
               @Value("${satellite.config.message.skywalker:#{null}}") String[] skywalkerMessage,
               @Value("${satellite.config.message.sato:#{null}}") String[] satoMessage) {
        Satellite kenobi = Satellite.builder()
                .name("kenobi")
                .message(kenobiMessage)
                .build();

        Satellite skywalker = Satellite.builder()
                .name("skywalker")
                .message(skywalkerMessage)
                .build();

        Satellite sato = Satellite.builder()
                .name("sato")
                .message(satoMessage)
                .build();

        satellites = new Satellite[] { kenobi, skywalker, sato };
    }

    @Test
    void getMessage() {
        Assertions.assertEquals("este es un mensaje secreto",messageService.getMessage(satellites));
    }

    @Test
    void whenMessageIsBlank_thenMessageException() {
        Satellite kenobi = Satellite.builder()
                .name("kenobi")
                .message(new String[] {"","","","",""})
                .build();

        List<Satellite> satelitesAlt = Arrays.stream(satellites)
                .filter(s -> !s.getName().equals("kenobi"))
                .collect(Collectors.toList());

        satelitesAlt.add(kenobi);

        Assertions.assertThrows(MessageException.class, () -> messageService.getMessage(satelitesAlt.toArray(Satellite[]::new)));
    }

    @Test
    void whenMessageIsNull_thenMessageException() {
        Arrays.stream(satellites).forEach(s -> s.setMessage(null));

        Assertions.assertThrows(MessageException.class, () -> messageService.getMessage(satellites));
    }

}
