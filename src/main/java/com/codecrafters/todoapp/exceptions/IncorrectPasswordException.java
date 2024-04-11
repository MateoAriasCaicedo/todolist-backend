package com.codecrafters.todoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect password")
public class IncorrectPasswordException extends RuntimeException{

  public IncorrectPasswordException(String password, String userName) {
    super("Incorrect password: " + password + ", for the user " + userName);
  }

}
