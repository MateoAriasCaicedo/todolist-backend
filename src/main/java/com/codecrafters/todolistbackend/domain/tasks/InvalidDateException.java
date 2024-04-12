package com.codecrafters.todolistbackend.domain.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid date")
class InvalidDateException extends RuntimeException {

  InvalidDateException(String date) {
    super("Invalid date: " + date);
  }
}
