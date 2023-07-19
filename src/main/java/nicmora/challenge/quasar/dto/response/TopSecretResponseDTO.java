package nicmora.challenge.quasar.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TopSecretResponseDTO {

    private Map<String, Float> location;
    private String message;

}
