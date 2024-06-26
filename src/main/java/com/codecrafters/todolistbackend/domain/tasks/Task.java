package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.database.fields.TaskFields;
import java.util.List;
import org.bson.Document;

public record Task(
    String id,
    String title,
    String description,
    String dueDate,
    String category,
    boolean completed,
    List<String> tags) {

  static Task fromDocument(Document document) {
    return new Task(
        document.getObjectId(TaskFields.ID).toHexString(),
        document.get(TaskFields.TITLE, String.class),
        document.get(TaskFields.DESCRIPTION, String.class),
        document.get(TaskFields.DUE_DATE, String.class),
        document.get(TaskFields.CATEGORY, String.class),
        document.get(TaskFields.COMPLETED, Boolean.class),
        document.getList(TaskFields.TAGS, String.class));
  }

  public String toString() {
    return id
        + "\n"
        + title
        + "\n"
        + description
        + "\n"
        + dueDate
        + "\n"
        + category
        + "\n"
        + completed
        + "\n"
        + String.join("-", tags);
  }
}
