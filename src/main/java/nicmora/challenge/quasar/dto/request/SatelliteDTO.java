package nicmora.challenge.quasar.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SatelliteDTO {

    private String name;
    private Float distance;
    private String[] message;

}
