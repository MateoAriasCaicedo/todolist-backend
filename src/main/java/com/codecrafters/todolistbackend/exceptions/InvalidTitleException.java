package com.codecrafters.todolistbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid title")
public class InvalidTitleException extends RuntimeException{
  public InvalidTitleException(String title) {
    super("Invalid date: " + title);
  }
}
