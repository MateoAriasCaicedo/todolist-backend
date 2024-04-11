package com.codecrafters.todoapp.domain.tasks;

import org.bson.types.ObjectId;

public class TaskDoesNotExistsException extends RuntimeException {

  public TaskDoesNotExistsException(ObjectId taskID) {
    super("There is no task with ID: " + taskID);
  }
}
