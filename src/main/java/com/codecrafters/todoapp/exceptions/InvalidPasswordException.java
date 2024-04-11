package com.codecrafters.todoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid password")
public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(String password) {
    super("Password length is less than 8 carcateres: " + password);
  }

}
