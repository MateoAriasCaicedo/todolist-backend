package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class InvalidEmailException extends RuntimeException {

  InvalidEmailException(String email) {
    super("Invalid email: " + email);
  }
}
