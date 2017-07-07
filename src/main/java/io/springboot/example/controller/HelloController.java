package io.springboot.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * Play with localization!
 *
 * @author shekh akther
 */
@RestController
public class HelloController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> sayHello() throws JsonProcessingException {
        String message = messageSource.getMessage("message.hello", null, LocaleContextHolder.getLocale());
        return new ResponseEntity(objectMapper.writeValueAsString(message), HttpStatus.OK);
    }


    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, params = {"ln"})
    public ResponseEntity<?> sayHello(@RequestParam(value = "ln") String ln) throws JsonProcessingException {
        Locale locale = new Locale(ln);
        String message = messageSource.getMessage("message.hello", null, locale);
        return new ResponseEntity(objectMapper.writeValueAsString(message), HttpStatus.OK);
    }




}
