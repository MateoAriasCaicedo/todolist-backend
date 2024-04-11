package com.codecrafters.todoapp.domain.users;

import com.codecrafters.todoapp.db.collections.fields.UserFields;
import com.codecrafters.todoapp.domain.tasks.Task;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public record UserCreationDTO(
    String firstName,
    String lastName,
    String username,
    String email,
    String password,
    List<Task> tasks,
    List<String> categories) {

  Document asMongoDocument() {
    return new Document(UserFields.ID, new ObjectId())
        .append(UserFields.FIRST_NAME, this.firstName)
        .append(UserFields.LAST_NAME, this.lastName)
        .append(UserFields.USERNAME, this.username)
        .append(UserFields.EMAIL, this.email)
        .append(UserFields.PASSWORD, this.password)
        .append(UserFields.TASKS, this.tasks)
        .append(UserFields.CATEGORIES, this.categories);
  }
}
