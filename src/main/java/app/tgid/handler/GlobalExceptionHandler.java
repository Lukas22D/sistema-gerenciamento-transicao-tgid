package app.tgid.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    // Trata exceções de RuntimeException (como "Usuário não encontrado")
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ResponseError> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ResponseError responseError = new ResponseError();
        responseError.setError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    // Trata exceções de parâmetros inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ResponseError responseError = new ResponseError();
        responseError.setError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // Trata exceções gerais
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseError> handleAllExceptions(Exception ex, WebRequest request) {
        ResponseError responseError = new ResponseError();
        responseError.setError("Erro inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

