package BTEC.ASM.project.common.exception;

import BTEC.ASM.project.common.response.ApiResponse;
import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.modules.academic.exception.*;
import BTEC.ASM.project.modules.identity.exception.RefreshTokenNotFoundException;
import BTEC.ASM.project.modules.identity.exception.UserNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===== VALIDATION =====
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        return ResponseData.fail(message, HttpStatus.BAD_REQUEST);
    }

    // ===== 404 =====
    @ExceptionHandler(TermNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleTermNotFound(
            TermNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExecutionControl.UserException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFound(
            UserNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ClassGroupNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleClassGroupNotFound(
            ClassGroupNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleSubjectNotFound(
            SubjectNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleRefreshTokenNotFound(
            RefreshTokenNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // ===== 409 =====
    @ExceptionHandler(ClassGroupAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleClassGroupAlreadyExists(
            ClassGroupNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(TermAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleTermAlreadyExists(
            TermAlreadyExistsException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(SubjectAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleSubjectAlreadyExists(
            SubjectAlreadyExistsException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // ===== 400 =====
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(
            IllegalArgumentException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // ===== 500 =====
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(
            RuntimeException ex
    ) {
        return ResponseData.fail("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
