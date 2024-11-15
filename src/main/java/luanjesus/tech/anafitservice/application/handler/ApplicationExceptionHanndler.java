package luanjesus.tech.anafitservice.application.handler;

import lombok.AllArgsConstructor;
import luanjesus.tech.anafitservice.presentation.dto.ResponseDto;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHanndler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(error.getField() + ": " + error.getDefaultMessage())
        );

        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setErrors(errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto<Void>> handleValidationExceptions(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponseDto(getMessage("auth.bad-credentials")));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponseDto(getMessage("exception.unexpected-error") + ex.getMessage()));
    }

    private String getMessage(String messageCode) {
        return messageSource.getMessage(messageCode, null, Locale.getDefault());
    }

    private ResponseDto<Void> buildResponseDto(String error) {
        return buildResponseDto(List.of(error));
    }

    private ResponseDto<Void> buildResponseDto(List<String> errors) {
        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setErrors(errors);

        return responseDto;
    }
}
