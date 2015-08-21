package com.swcguild.contactlistmvc.validation;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice //Advice as in AOP advice
//the ControllerAdvice annotation listens for any exception defined in the ExceptionHandler and activates if any method in any controller throws that exception
//ResponseStatus has to be anything in the 400's, but by convention it should be the BAD_REQUEST status
//
public class RestValidationHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class) //advice 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody //send JSON back
    public ValidationErrorContainer processValidationErrors(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult(); //returns 
        List<FieldError> fieldErrors = result.getFieldErrors(); //get list of errors out of bindingresult
        
        ValidationErrorContainer errors = new ValidationErrorContainer();
        for(FieldError currentError : fieldErrors) {
            errors.addValidationError(currentError.getField(), currentError.getDefaultMessage());
        }
        return errors; //these go into ResponseBody
    }
}
