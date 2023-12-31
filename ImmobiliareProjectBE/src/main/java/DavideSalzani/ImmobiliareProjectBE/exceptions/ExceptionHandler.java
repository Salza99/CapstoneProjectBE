package DavideSalzani.ImmobiliareProjectBE.exceptions;

import DavideSalzani.ImmobiliareProjectBE.exceptions.exceptionsPayloads.ErrorsListResponseDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.exceptionsPayloads.ErrorsResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsListResponseDTO handleBadRequestList(BadRequestException ex){
        if (ex.getErrorList() != null) {
            List<String> errorsList = ex.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return new ErrorsListResponseDTO(LocalDate.now(), errorsList);
        } else {
            return new ErrorsListResponseDTO(LocalDate.now(), new ArrayList<>());
        }
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsResponseDTO handleUnauthorized(UnauthorizedException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDate.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponseDTO handleMethodArgumentNotValidException(BadRequestException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDate.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponseDTO handleNotFound(NotFoundException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDate.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsResponseDTO handleAccessDenied(AccessDeniedException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDate.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorsResponseDTO handleUnauthorized(HttpRequestMethodNotSupportedException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDate.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponseDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsResponseDTO("we are sorry at the moment we have some internal problems, we are trying to resolve them", LocalDate.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponseDTO handleAlreadyExist(AlreadyExistException e) {

        return new ErrorsResponseDTO(e.getMessage(), LocalDate.now());
    }
}
