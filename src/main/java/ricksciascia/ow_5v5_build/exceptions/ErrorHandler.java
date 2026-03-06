package ricksciascia.ow_5v5_build.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ricksciascia.ow_5v5_build.dto.ErrorDTO;
import ricksciascia.ow_5v5_build.dto.ErrorWithListDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadReqException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadReq(BadReqException e) {
        return new ErrorDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ValException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorWithListDTO handleValException(ValException e) {
        return new ErrorWithListDTO(e.getMessage(),LocalDateTime.now(),e.getErrorList());
    }

}
