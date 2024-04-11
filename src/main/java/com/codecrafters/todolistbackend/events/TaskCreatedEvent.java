package com.codecrafters.todolistbackend.events;

import org.springframework.context.ApplicationEvent;

public class TaskCreatedEvent extends ApplicationEvent {

  public TaskCreatedEvent(Object source) {
    super(source);
  }
}
