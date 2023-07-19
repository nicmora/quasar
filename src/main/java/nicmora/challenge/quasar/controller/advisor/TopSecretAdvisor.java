package nicmora.challenge.quasar.controller.advisor;

import nicmora.challenge.quasar.controller.TopSecretController;
import nicmora.challenge.quasar.dto.http.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice(basePackageClasses = TopSecretController.class)
public class TopSecretAdvisor {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleRuntimeException(RuntimeException ex) {
        return Mono.just(ResponseEntity.internalServerError()
                .body(ErrorResponseDTO.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message(ex.getMessage())
                        .build()
                ));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleException(RuntimeException ex) {
        return Mono.just(ResponseEntity.internalServerError()
                .body(ErrorResponseDTO.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message("Unknown error")
                        .build()
                ));
    }

}
