package nicmora.challenge.quasar.service;

import nicmora.challenge.quasar.exception.LocationException;
import nicmora.challenge.quasar.model.Satellite;
import nicmora.challenge.quasar.utils.QuasarCalculator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service
public class QuasarLocationService {

    public Map<String, Float> getLocation(Satellite... satellites) {
        this.validate(satellites);

        double[] x = {satellites[0].getX(), satellites[1].getX(), satellites[2].getX()};
        double[] y = {satellites[0].getY(), satellites[1].getY(), satellites[2].getY()};
        double[] distance = {satellites[0].getDistance(), satellites[1].getDistance(), satellites[2].getDistance()};

        double targetX = QuasarCalculator.trilaterateX(x, y, distance);
        double targetY = QuasarCalculator.trilaterateY(x, y, distance);

        return Map.of("x", (float) targetX, "y", (float) targetY);
    }

    private void validate(Satellite... satellites) {
        if (satellites.length < 3)
            throw new LocationException("At least 3 satellites are required");

        if (Arrays.stream(satellites).anyMatch(satellite -> Objects.isNull(satellite.getX())
                || Objects.isNull(satellite.getY())
                || Objects.isNull(satellite.getDistance())))
            throw new LocationException("Position x, y and distance are required");
    }

}
