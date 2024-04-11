package com.codecrafters.todolistbackend.events.listeners;

import com.codecrafters.todolistbackend.events.TaskCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskCreatedEventListener {

  @EventListener(TaskCreatedEvent.class)
  public void handleEvent(TaskCreatedEvent event) {
    System.out.println(event);
  }
}
