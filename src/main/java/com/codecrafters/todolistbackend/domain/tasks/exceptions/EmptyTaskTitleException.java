package com.codecrafters.todolistbackend.domain.tasks.exceptions;

/** Thrown when there is an attempt to create a task with empty title. */
public class EmptyTaskTitleException extends RuntimeException {

  /** Creates a new exception indicating that the given task title was empty. */
  public EmptyTaskTitleException() {
    super("Invalid title, it can not be empty");
  }
}
