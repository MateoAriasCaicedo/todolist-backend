package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid email")
class InvalidEmailException extends RuntimeException {

  InvalidEmailException(String email) {
    super("Invalid email: " + email);
  }
}