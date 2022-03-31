package com.kodilla.car_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException() {
        return new ResponseEntity<>("A client with the given phone number doesn't exist",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientFoundInDatabaseException.class)
    public ResponseEntity<Object> handleClientFoundInDatabaseException() {
        return new ResponseEntity<>("A client with the given phone number exists in database",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceTechnicianNotFoundException.class)
    public ResponseEntity<Object> handleServiceTechnicianNotFoundException() {
        return new ResponseEntity<>("A service technician with the given id doesn't exist",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RepairNotFoundException.class)
    public ResponseEntity<Object> handleRepairNotFoundException() {
        return new ResponseEntity<>("A repair with the given id doesn't exist",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException() {
        return new ResponseEntity<>("A car with the given vin doesn't exist",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarFoundInDatabaseException.class)
    public ResponseEntity<Object> handleCarFoundInDatabaseException() {
        return new ResponseEntity<>("A car with the given vin already exist in database",
                HttpStatus.BAD_REQUEST);
    }
}