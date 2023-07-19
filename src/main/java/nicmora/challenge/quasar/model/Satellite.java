package nicmora.challenge.quasar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Satellite {

    private String name;
    private Float x;
    private Float y;
    private Float distance;
    private String[] message;

}
