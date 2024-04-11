package com.codecrafters.todoapp.domain.tasks;

import com.codecrafters.todoapp.db.collections.fields.TaskFields;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

record TaskCreationDTO(
    String title,
    String description,
    String dueDate,
    String category,
    Boolean completed,
    List<String> tags) {

  Document asMongoDocument() {
    return new Document(TaskFields.ID, new ObjectId())
        .append(TaskFields.TITLE, this.title)
        .append(TaskFields.DESCRIPTION, this.description)
        .append(TaskFields.DUE_DATE, this.dueDate)
        .append(TaskFields.CATEGORY, this.category)
        .append(TaskFields.COMPLETED, Boolean.TRUE)
        .append(TaskFields.TAGS, this.tags);
  }
}
