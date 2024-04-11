package com.codecrafters.todolistbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User username or email already exists.")
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String userName, String email) {
    super("User username or email already exists: " + userName + ", " + email);
  }

}
