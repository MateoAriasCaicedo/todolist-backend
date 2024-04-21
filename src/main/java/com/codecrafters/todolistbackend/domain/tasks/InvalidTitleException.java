package com.codecrafters.todolistbackend.domain.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class InvalidTitleException extends RuntimeException {
  InvalidTitleException(String title) {
    super("Invalid date: " + title);
  }
}
