package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.database.fields.TaskFields;
import org.bson.Document;
import org.bson.types.ObjectId;

class TaskCreationDTOMapper {

  private TaskCreationDTOMapper() {}

  static TaskCreationDTO fromDocument(Document taskDocument) throws IllegalArgumentException {

    if (taskDocument == null) {
      throw new IllegalArgumentException("The task document cannot be null");
    }

    return new TaskCreationDTO(
        taskDocument.getString(TaskFields.TITLE),
        taskDocument.getString(TaskFields.DESCRIPTION),
        taskDocument.getString(TaskFields.DUE_DATE),
        taskDocument.getString(TaskFields.CATEGORY),
        taskDocument.getBoolean(TaskFields.COMPLETED),
        taskDocument.getList(TaskFields.TAGS, String.class));
  }

  static Document asMongoDocument(TaskCreationDTO task) throws IllegalArgumentException {

    if (task == null) {
      throw new IllegalArgumentException("The task creation dto must not be null");
    }

    return new Document(TaskFields.ID, new ObjectId())
        .append(TaskFields.TITLE, task.title())
        .append(TaskFields.DESCRIPTION, task.description())
        .append(TaskFields.DUE_DATE, task.dueDate())
        .append(TaskFields.CATEGORY, task.category())
        .append(TaskFields.COMPLETED, task.completed())
        .append(TaskFields.TAGS, task.tags());
  }
}
