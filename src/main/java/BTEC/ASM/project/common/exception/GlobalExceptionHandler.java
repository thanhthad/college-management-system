package BTEC.ASM.project.common.exception;

import BTEC.ASM.project.common.response.ApiResponse;
import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.modules.academic.exception.InvalidDateRangeException;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupNotFoundException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingConflictException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingNotFoundException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectNotFoundException;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermNotFoundException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenExpiredException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenNotFoundException;
import BTEC.ASM.project.modules.identity.exception.user.UserNotFoundException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenRevokedException;
import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

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

    @ExceptionHandler(OfferingNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleOfferingNotFound(
            OfferingNotFoundException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "OFFERING_EVENT | action=OFFERING | userId={} | status=FAIL | reason=OFFERING_NOT_FOUND",
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

    @ExceptionHandler(OfferingAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleOfferingAlreadyExists(
            OfferingAlreadyExistsException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "OFFERING_EVENT | action=OFFERING | userId={} | status=FAIL | reason=OFFERING_EXISTS",
                userId
        );
        return ResponseData.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

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

    @ExceptionHandler(OfferingConflictException.class)
    public ResponseEntity<ApiResponse<Object>> handleOfferingConflict(
            OfferingConflictException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "OFFERING_EVENT | action=CREATE_OR_UPDATE | userId={} | status=FAIL | reason=OFFERING_CONFLICT",
                userId
        );

        return ResponseData.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.error(
                "DATABASE_EVENT | action=JPA_SAVE | userId={} | status=FAIL | reason=DATA_INTEGRITY_VIOLATION",
                userId,
                ex
        );

        return ResponseData.fail(
                "Data integrity violation",
                HttpStatus.CONFLICT
        );
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

    // ===== 400 – ENUM / PARAM TYPE MISMATCH =====
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex
    ) {
        String message;

        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            message = String.format(
                    "Invalid value '%s' for parameter '%s'. Allowed values are: %s",
                    ex.getValue(),
                    ex.getName(),
                    Arrays.toString(ex.getRequiredType().getEnumConstants())
            );
        } else {
            message = String.format(
                    "Invalid value '%s' for parameter '%s'",
                    ex.getValue(),
                    ex.getName()
            );
        }

        log.warn(
                "TYPE_MISMATCH | userId={} | message={}",
                getIdFromAuthentication(),
                message
        );

        return ResponseData.fail(message, HttpStatus.BAD_REQUEST);
    }

    // ===== BUSINESS RULE =====
    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidDateRange(
            InvalidDateRangeException ex
    ) {
        Long userId = getIdFromAuthentication();

        log.warn(
                "OFFERING_EVENT | action=DATE_RANGE | userId={} | status=FAIL | reason=INVALID_DATE_RANGE",
                userId
        );

        return ResponseData.fail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    // ===== 400 – VALIDATION BODY =====
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        log.warn(
                "VALIDATION_ERROR | userId={} | message={}",
                getIdFromAuthentication(),
                message
        );

        return ResponseData.fail(message, HttpStatus.BAD_REQUEST);
    }

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

    // ===== 500 – CATCH ALL =====
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception ex
    ) {
        log.error(
                "INTERNAL_SERVER_ERROR | userId={}",
                getIdFromAuthentication(),
                ex
        );

        return ResponseData.fail(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}