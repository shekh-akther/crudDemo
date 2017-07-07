package io.springboot.example.exception;

import java.util.List;

/**
 * @author shekh akther
 */
public class ValidationResponse {

    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
