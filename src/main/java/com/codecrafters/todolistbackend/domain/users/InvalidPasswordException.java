package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid password")
class InvalidPasswordException extends RuntimeException {

  InvalidPasswordException(String password) {
    super("The given password is invalid: " + password);
  }
}
