package BTEC.ASM.project.common.exception;

import BTEC.ASM.project.common.response.ApiResponse;
import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupNotFoundException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectNotFoundException;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermNotFoundException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenExpiredException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenNotFoundException;
import BTEC.ASM.project.modules.identity.exception.UserNotFoundException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenRevokedException;
import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Long getIdFromAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getId();
    }

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
        Long userId = getIdFromAuthentication();

        log.warn(
                "TERM_EVENT | action=TERM | userId={} | status=FAIL | reason=TERM_NOT_FOUND",
                userId
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFound(
            UserNotFoundException ex
    ) {
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClassGroupNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleClassGroupNotFound(
            ClassGroupNotFoundException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "CLASSGROUP_EVENT | action=CLASSGROUP | userId={} | status=FAIL | reason=CLASSGROUP_NOT_FOUND",
                userId
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleSubjectNotFound(
            SubjectNotFoundException ex
    ) {
        log.warn(
                "SUBJECT_EVENT | action=SUBJECT | userId={} | status=FAIL | reason=SUBJECT_NOT_FOUND",
                getIdFromAuthentication()
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleRefreshTokenNotFound(
            RefreshTokenNotFoundException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "AUTH_EVENT | action=REFRESH_TOKEN_REFRESH | userId={} | status=FAIL | reason=TOKEN_NOT_FOUND",
                userId
        );

        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ApiResponse<Object>> handleRefreshTokenExpired(
            RefreshTokenExpiredException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "AUTH_EVENT | action=REFRESH_TOKEN_REFRESH | userId={} | status=FAIL | reason=TOKEN_EXPIRED",
                userId
        );

        return ResponseData.fail(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(RefreshTokenRevokedException.class)
    public ResponseEntity<ApiResponse<Object>> handleRefreshTokenRevoked(
            RefreshTokenRevokedException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "AUTH_EVENT | action=REFRESH_TOKEN_REFRESH | userId={} | status=FAIL | reason=TOKEN_REVOKED",
                userId
        );

        return ResponseData.fail(ex.getMessage(), HttpStatus.FORBIDDEN);
    }



    // ===== 409 =====
    @ExceptionHandler(ClassGroupAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleClassGroupAlreadyExists(
            ClassGroupAlreadyExistsException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "CLASSGROUP_EVENT | action=CLASSGROUP | userId={} | status=FAIL | reason=CLASSGROUP_EXISTS",
                userId
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(TermAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleTermAlreadyExists(
            TermAlreadyExistsException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "TERM_EVENT | action=TERM | userId={} | status=FAIL | reason=TERM_EXISTS",
                userId
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(SubjectAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleSubjectAlreadyExists(
            SubjectAlreadyExistsException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "SUBJECT_EVENT | action=SUBJECT | userId={} | status=FAIL | reason=SUBJECT_EXISTS",
                userId
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // ===== 400 =====
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(
            IllegalArgumentException ex
    ) {
        log.warn(
                "VALIDATION_FAIL | userId={} | message={}",
                getIdFromAuthentication(),
                ex
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalState(
            IllegalStateException ex
    ) {
        log.warn(
                "VALIDATION_FAIL | userId={} | message={}",
                getIdFromAuthentication(),
                ex
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // ===== 500 =====
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(
            RuntimeException ex
    ) {
        log.warn(
                "INTERNAL_SERVER_ERROR | userId={} | message={}",
                getIdFromAuthentication(),
                ex
        );
        return ResponseData.fail("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}