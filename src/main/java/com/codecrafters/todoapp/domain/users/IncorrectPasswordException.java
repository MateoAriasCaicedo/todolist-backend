package com.codecrafters.todoapp.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect password for the userName")
public class IncorrectPasswordException extends RuntimeException {

  public IncorrectPasswordException(String password, String userName) {
    super("This password " + password + " is incorrect for the user: " + userName);
  }
}
