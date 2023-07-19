package nicmora.challenge.quasar.service;

import nicmora.challenge.quasar.exception.MessageException;
import nicmora.challenge.quasar.model.Satellite;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuasarMessageService {

    public String getMessage(Satellite... satellites) {
        this.validate(satellites);

        int maxLength = Arrays.stream(satellites)
                .map(Satellite::getMessage)
                .mapToInt(m -> m.length)
                .max()
                .orElse(0);

        String[] result = new String[maxLength];

        Arrays.stream(satellites)
                .map(Satellite::getMessage)
                .forEach(msg -> this.buildMessage(msg, result));

        return this.sanitize(result);
    }

    private void validate(Satellite... satellites) {
        if (satellites.length < 3)
            throw new MessageException("At least 3 satellites are required");

        if (Arrays.stream(satellites).anyMatch(satellite -> Objects.isNull(satellite.getMessage())))
            throw new MessageException("Message is required");
    }

    private void buildMessage(String[] message, String[] result) {
        AtomicInteger index = message.length < result.length ? new AtomicInteger(1) : new AtomicInteger(0);
        Arrays.stream(message)
                .toList()
                .forEach(word -> {
                    Optional.of(word)
                            .filter(w -> !w.isBlank())
                            .ifPresent(w -> result[index.get()] = w);
                    index.getAndIncrement();
                });
    }

    private String sanitize(String[] result) {
        if (Arrays.stream(result).anyMatch(Strings::isBlank))
            throw new MessageException("Message could not be completed");

        result = result[0].isBlank() ? Arrays.copyOfRange(result, 1, result.length) : result;

        return String.join(" ", result);
    }

}
