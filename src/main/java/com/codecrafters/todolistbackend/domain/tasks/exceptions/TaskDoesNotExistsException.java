package com.codecrafters.todolistbackend.domain.tasks.exceptions;

import org.bson.types.ObjectId;

/** Thrown when there is a query asking for a specific task, but it doesn't exist. */
public class TaskDoesNotExistsException extends RuntimeException {

  /**
   * Creates a new exception indicating that the task with the given ID does not exist.
   *
   * @param taskID The task ID that does not exist.
   */
  public TaskDoesNotExistsException(ObjectId taskID) {
    super("There is no task with ID: " + taskID);
  }
}
