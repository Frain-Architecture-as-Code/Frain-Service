package com.frain.frainapi.shared.interfaces.rest;

import com.frain.frainapi.organizations.domain.exceptions.*;
import com.frain.frainapi.projects.domain.exceptions.*;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import com.frain.frainapi.shared.domain.exceptions.JwtIncompleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ========== API Key Exceptions ==========

    @ExceptionHandler(ApiKeyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleApiKeyNotFound(ApiKeyNotFoundException ex) {
        logger.warn("API key not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("API_KEY_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidApiKey(InvalidApiKeyException ex) {
        logger.warn("Invalid API key: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of("INVALID_API_KEY", ex.getMessage()));
    }

    @ExceptionHandler(ApiKeyAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleApiKeyAlreadyExists(ApiKeyAlreadyExistsException ex) {
        logger.warn("API key already exists: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of("API_KEY_ALREADY_EXISTS", ex.getMessage()));
    }

    @ExceptionHandler(CannotManageApiKeyException.class)
    public ResponseEntity<ErrorResponse> handleCannotManageApiKey(CannotManageApiKeyException ex) {
        logger.warn("Cannot manage API key: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of("CANNOT_MANAGE_API_KEY", ex.getMessage()));
    }

    // ========== Project Exceptions ==========

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFound(ProjectNotFoundException ex) {
        logger.warn("Project not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("PROJECT_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(ProjectNameRequiredException.class)
    public ResponseEntity<ErrorResponse> handleProjectNameRequired(ProjectNameRequiredException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("PROJECT_NAME_REQUIRED", ex.getMessage()));
    }

    @ExceptionHandler(InvalidProjectNameLengthException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProjectNameLength(InvalidProjectNameLengthException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("INVALID_PROJECT_NAME_LENGTH", ex.getMessage()));
    }

    @ExceptionHandler(InvalidC4ModelSchemaException.class)
    public ResponseEntity<ErrorResponse> handleInvalidC4ModelSchema(InvalidC4ModelSchemaException ex) {
        logger.warn("Invalid C4Model schema: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("INVALID_C4MODEL_SCHEMA", ex.getMessage()));
    }

    @ExceptionHandler(ViewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleViewNotFound(ViewNotFoundException ex) {
        logger.warn("View not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("VIEW_NOT_FOUND", ex.getMessage()));
    }

    // ========== Organization Exceptions ==========

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrganizationNotFound(OrganizationNotFoundException ex) {
        logger.warn("Organization not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("ORGANIZATION_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFoundException ex) {
        logger.warn("Member not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("MEMBER_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundInOrganizationException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundInOrganization(UserNotFoundInOrganizationException ex) {
        logger.warn("User not found in organization: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of("USER_NOT_IN_ORGANIZATION", ex.getMessage()));
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvitationNotFound(InvitationNotFoundException ex) {
        logger.warn("Invitation not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("INVITATION_NOT_FOUND", ex.getMessage()));
    }

    // ========== JWT Exceptions ==========

    @ExceptionHandler(JwtIncompleteException.class)
    public ResponseEntity<ErrorResponse> handleJwtIncomplete(JwtIncompleteException ex) {
        logger.warn("JWT incomplete: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of("JWT_INCOMPLETE", ex.getMessage()));
    }

    // ========== Permission Exceptions ==========

    @ExceptionHandler(InsufficientPermissionsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPermissions(InsufficientPermissionsException ex) {
        logger.warn("Insufficient permissions: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of("INSUFFICIENT_PERMISSIONS", ex.getMessage()));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException ex) {
        logger.warn("Security exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of("ACCESS_DENIED", ex.getMessage()));
    }

    // ========== Validation Exceptions ==========

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        
        logger.warn("Validation failed: {}", fieldErrors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("VALIDATION_ERROR", "Validation failed", fieldErrors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Illegal argument: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("INVALID_ARGUMENT", ex.getMessage()));
    }

    // ========== Generic Exception Handler ==========

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("INTERNAL_ERROR", "An unexpected error occurred"));
    }

    // ========== Error Response Record ==========

    public record ErrorResponse(
            String code,
            String message,
            Map<String, String> details,
            Instant timestamp
    ) {
        public static ErrorResponse of(String code, String message) {
            return new ErrorResponse(code, message, null, Instant.now());
        }

        public static ErrorResponse of(String code, String message, Map<String, String> details) {
            return new ErrorResponse(code, message, details, Instant.now());
        }
    }
}
