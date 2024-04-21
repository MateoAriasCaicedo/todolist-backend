package com.codecrafters.todolistbackend.domain.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class InvalidDateException extends RuntimeException {

  InvalidDateException(String date) {
    super("Invalid date: " + date);
  }
}
