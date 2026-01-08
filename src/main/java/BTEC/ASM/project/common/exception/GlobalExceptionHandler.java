package BTEC.ASM.project.common.exception;

import BTEC.ASM.project.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Object> response = new ApiResponse<>();
        response.setMessage("Validation failed");
        response.setStatus_code(HttpStatus.BAD_REQUEST.value());
        response.setData(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(
            IllegalArgumentException ex
    ) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setMessage(ex.getMessage());
        response.setStatus_code(HttpStatus.BAD_REQUEST.value());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(
            RuntimeException ex
    ) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setMessage(ex.getMessage());
        response.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception ex
    ) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setMessage("Internal server error");
        response.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
