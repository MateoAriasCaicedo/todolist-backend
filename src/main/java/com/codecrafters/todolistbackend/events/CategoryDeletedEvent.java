package com.codecrafters.todolistbackend.events;

import org.springframework.context.ApplicationEvent;

public class CategoryDeletedEvent extends ApplicationEvent {

  public CategoryDeletedEvent(Object source) {
    super(source);
  }
}
