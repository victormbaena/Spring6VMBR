package com.victormbaena.spring6restmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class could be deleted.
 * See {@link NotFoundException} with annotation @ResponseStatus().
 */
@Slf4j
//@ControllerAdvice
public class ExceptionController {

    /**
     * In this case, you can use the @ControllerAdvice to launch exceptions in all controllers of application.
     * See {@link com.victormbaena.spring6restmvc.controller/CustomerControllerTest} for view the result.
     *
     * @return ResponseEntity
     */
    //@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpStatus> handleNotFoundException() {
        log.info("ExceptionController class and Handler method");
        return ResponseEntity.notFound().build();
    }
}
