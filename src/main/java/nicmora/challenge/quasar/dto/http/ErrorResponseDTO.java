package nicmora.challenge.quasar.dto.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {

    private int code;
    private String status;
    private String message;

}
