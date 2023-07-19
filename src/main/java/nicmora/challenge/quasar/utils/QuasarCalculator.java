package nicmora.challenge.quasar.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuasarCalculator {

    public static double trilaterateX(double[] x, double[] y, double[] distance) {
        double A = 2 * (x[0] - x[2]);
        double B = 2 * (y[0] - y[2]);
        double C = distance[2] * distance[2] - distance[0] * distance[0] - x[2] * x[2] + x[0] * x[0] - y[2] * y[2] + y[0] * y[0];

        double D = 2 * (x[1] - x[2]);
        double E = 2 * (y[1] - y[2]);
        double F = distance[2] * distance[2] - distance[1] * distance[1] - x[2] * x[2] + x[1] * x[1] - y[2] * y[2] + y[1] * y[1];

        return (C * E - F * B) / (E * A - B * D);
    }

    public static double trilaterateY(double[] x, double[] y, double[] distance) {
        double A = 2 * (x[0] - x[2]);
        double B = 2 * (y[0] - y[2]);
        double C = distance[2] * distance[2] - distance[0] * distance[0] - x[2] * x[2] + x[0] * x[0] - y[2] * y[2] + y[0] * y[0];

        double D = 2 * (x[1] - x[2]);
        double E = 2 * (y[1] - y[2]);
        double F = distance[2] * distance[2] - distance[1] * distance[1] - x[2] * x[2] + x[1] * x[1] - y[2] * y[2] + y[1] * y[1];

        return (C * D - A * F) / (D * B - A * E);
    }

}
