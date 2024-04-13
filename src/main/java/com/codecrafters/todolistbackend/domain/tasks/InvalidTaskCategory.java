package com.codecrafters.todolistbackend.domain.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid category")
public class InvalidTaskCategory extends RuntimeException {

  InvalidTaskCategory(String category) {
    super("The user has not the specified task category: " + category);
  }
}
