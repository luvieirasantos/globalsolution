package com.fiap.globalsolution.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Constraint Violation");
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Resource Not Found");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        String message = extractConstraintMessage(ex);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflito de Dados");
        response.put("message", message);
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateResource(
            DuplicateResourceException ex, WebRequest request) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Recurso Duplicado");
        response.put("message", ex.getMessage());
        response.put("resource", ex.getResource());
        response.put("field", ex.getField());
        response.put("value", ex.getValue());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", messageSource.getMessage("error.internal", null, LocaleContextHolder.getLocale()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String extractConstraintMessage(DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        if (message == null) {
            return "Violação de restrição de integridade de dados.";
        }

        // Pattern para Oracle ORA-00001
        Pattern oraclePattern = Pattern.compile("ORA-00001.*violada.*\\((.+)\\)");
        Matcher oracleMatcher = oraclePattern.matcher(message);

        if (oracleMatcher.find()) {
            String constraintName = oracleMatcher.group(1);

            // Mapear nomes de constraints para mensagens amigáveis
            switch (constraintName.toLowerCase()) {
                case "sys_c006327968": // CPF funcionario
                    return "CPF já cadastrado no sistema. Utilize um CPF diferente.";
                case "sys_c006327937": // CNPJ empresa
                    return "CNPJ já cadastrado no sistema. Utilize um CNPJ diferente.";
                case "sys_c006327936": // Email funcionario
                    return "Email já cadastrado no sistema. Utilize um email diferente.";
                default:
                    if (constraintName.contains("cpf") || constraintName.contains("funcionario")) {
                        return "CPF já cadastrado no sistema.";
                    } else if (constraintName.contains("cnpj") || constraintName.contains("empresa")) {
                        return "CNPJ já cadastrado no sistema.";
                    } else if (constraintName.contains("email")) {
                        return "Email já cadastrado no sistema.";
                    }
                    return "Registro já existe no sistema. Verifique os dados e tente novamente.";
            }
        }

        // Pattern para PostgreSQL unique violation
        Pattern postgresPattern = Pattern.compile("duplicate key value violates unique constraint \"(.+)\"");
        Matcher postgresMatcher = postgresPattern.matcher(message);

        if (postgresMatcher.find()) {
            String constraintName = postgresMatcher.group(1);
            if (constraintName.contains("cpf")) {
                return "CPF já cadastrado no sistema.";
            } else if (constraintName.contains("cnpj")) {
                return "CNPJ já cadastrado no sistema.";
            } else if (constraintName.contains("email")) {
                return "Email já cadastrado no sistema.";
            }
        }

        // Pattern para MySQL duplicate entry
        Pattern mysqlPattern = Pattern.compile("Duplicate entry '(.+)' for key '(.+)'");
        Matcher mysqlMatcher = mysqlPattern.matcher(message);

        if (mysqlMatcher.find()) {
            String value = mysqlMatcher.group(1);
            String key = mysqlMatcher.group(2);

            if (key.contains("cpf")) {
                return "CPF '" + value + "' já cadastrado no sistema.";
            } else if (key.contains("cnpj")) {
                return "CNPJ '" + value + "' já cadastrado no sistema.";
            } else if (key.contains("email")) {
                return "Email '" + value + "' já cadastrado no sistema.";
            }
            return "Valor '" + value + "' já cadastrado no sistema.";
        }

        return "Registro duplicado. Verifique se os dados já existem no sistema.";
    }
}
