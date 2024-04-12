package com.codecrafters.todolistbackend.domain.tasks;

import org.bson.types.ObjectId;

class TaskDoesNotExistsException extends RuntimeException {

  TaskDoesNotExistsException(ObjectId taskID) {
    super("There is no task with ID: " + taskID);
  }
}
