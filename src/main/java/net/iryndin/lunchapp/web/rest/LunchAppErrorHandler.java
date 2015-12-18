package net.iryndin.lunchapp.web.rest;

import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.error.AuthorizationException;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.error.LunchAppBasicException;
import net.iryndin.lunchapp.web.model.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;

/**
 * Error handler for available exceptions
 */
@ControllerAdvice(basePackages = {"net.iryndin.lunchapp.web.rest"})
public class LunchAppErrorHandler {

    /**
     * Handler for EntityDeletedException
     * @return entity with JSON error object
     */
    @ResponseBody
    @ExceptionHandler(EntityDeletedException.class)
    public ResponseEntity<?> handleEntityDeletedException() {
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "There is no such entity", null), HttpStatus.NOT_FOUND);
    }

    /**
     * Handler for EntityNotFoundException
     * @return entity with JSON error object
     */
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException() {
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "There is no such entity", null), HttpStatus.NOT_FOUND);
    }

    /**
     * Handler for AuthenticationException
     * @return entity with JSON error object
     */
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ae) {
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "Cannot authenticate user " + ae.getUsername(), null), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handler for AuthorizationException
     * @return entity with JSON error object
     */
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException ae) {
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "Cannot authorize user " + ae.getUsername(), null), HttpStatus.FORBIDDEN);
    }

    /**
     * Handler for AuthenticationException
     * @return entity with JSON error object
     */
    @ResponseBody
    @ExceptionHandler(LunchAppBasicException.class)
    public ResponseEntity<?> handleLunchAppBasicException() {
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "Internal server error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler for all other exceptions
     * @return entity with JSON error object
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException() {
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "Internal server error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
