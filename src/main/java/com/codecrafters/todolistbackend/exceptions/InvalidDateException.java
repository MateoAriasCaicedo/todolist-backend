package com.codecrafters.todolistbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid date")
public class InvalidDateException extends RuntimeException {

  public InvalidDateException(String date) {
    super("Invalid date: " + date);
  }

}
