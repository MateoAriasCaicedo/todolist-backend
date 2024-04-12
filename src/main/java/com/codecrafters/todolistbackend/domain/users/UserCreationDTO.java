package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import org.bson.Document;
import org.bson.types.ObjectId;

record UserCreationDTO(
    String firstName, String lastName, String username, String email, String password) {

  Document asMongoDocument() {
    return new Document(UserFields.ID, new ObjectId())
        .append(UserFields.FIRST_NAME, this.firstName)
        .append(UserFields.LAST_NAME, this.lastName)
        .append(UserFields.USERNAME, this.username)
        .append(UserFields.EMAIL, this.email)
        .append(UserFields.PASSWORD, this.password);
  }
}
