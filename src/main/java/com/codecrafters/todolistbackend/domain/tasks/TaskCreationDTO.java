package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.database.fields.TaskFields;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * DTO used for creating a task.
 *
 * @param title The task title.
 * @param description The task description.
 * @param dueDate The task due date in the format "YYYY-MM-DD".
 * @param category The task category
 * @param completed Whether the task is completed or not.
 * @param tags The task tags associated with the task.
 */

public record TaskCreationDTO(
    String title,
    String description,
    String dueDate,
    String category,
    Boolean completed,
    List<String> tags) {

  static TaskCreationDTO fromDocument(Document taskDocument) {
    return new TaskCreationDTO(
        taskDocument.getString(TaskFields.TITLE),
        taskDocument.getString(TaskFields.DESCRIPTION),
        taskDocument.getString(TaskFields.DUE_DATE),
        taskDocument.getString(TaskFields.CATEGORY),
        taskDocument.getBoolean(TaskFields.COMPLETED),
        taskDocument.getList(TaskFields.TAGS, String.class));
  }

  Document asMongoDocument() {
    return new Document(TaskFields.ID, new ObjectId())
        .append(TaskFields.TITLE, this.title)
        .append(TaskFields.DESCRIPTION, this.description)
        .append(TaskFields.DUE_DATE, this.dueDate)
        .append(TaskFields.CATEGORY, this.category)
        .append(TaskFields.COMPLETED, this.completed)
        .append(TaskFields.TAGS, this.tags);
  }
}
