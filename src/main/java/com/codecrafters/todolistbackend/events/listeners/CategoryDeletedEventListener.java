package com.codecrafters.todolistbackend.events.listeners;

import com.codecrafters.todolistbackend.events.CategoryDeletedEvent;
import org.springframework.context.ApplicationListener;

public class CategoryDeletedEventListener implements ApplicationListener<CategoryDeletedEvent> {

  @Override
  public void onApplicationEvent(CategoryDeletedEvent event) {
    System.out.println("Category deleted: " + event.getSource());
  }
}
