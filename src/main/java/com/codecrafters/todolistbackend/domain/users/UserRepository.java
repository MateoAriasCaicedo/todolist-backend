package com.codecrafters.todolistbackend.domain.users;

import static com.mongodb.client.model.Filters.eq;

import com.codecrafters.todolistbackend.db.DBCollections;
import com.codecrafters.todolistbackend.db.DBNames;
import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.codecrafters.todolistbackend.db.indexes.UsersIndex;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import java.util.List;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
class UserRepository {

  private final MongoClient mongoClient;

  UserRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  String createUser(UserCreationDTO user) throws UserDoesNotExistsException {
    try {
      return mongoClient
          .getDatabase(DBNames.TODO)
          .getCollection(DBCollections.USERS)
          .insertOne(
              user.asMongoDocument()
                  .append(UserFields.TASKS, List.of())
                  .append(UserFields.CATEGORIES, List.of()))
          .getInsertedId()
          .asObjectId()
          .getValue()
          .toHexString();
    } catch (MongoWriteException exception) {
      switch (UsersIndex.findViolatedIndex(exception.getMessage())) {
        case UNIQUE_USERNAME -> throw new UsernameAlreadyExists();
        case UNIQUE_EMAIL -> throw new EmailAlreadyExists();
        default -> throw new RuntimeException("Unexpected side error");
      }
    }
  }

  String findUser(String username, String password) throws UserDoesNotExistsException {
    Document user =
        mongoClient
            .getDatabase(DBNames.TODO)
            .getCollection(DBCollections.USERS)
            .find(eq(UserFields.USERNAME, username))
            .first();

    if (user == null) {
      throw new UserDoesNotExistsException(username);
    }

    if (!user.getString(UserFields.PASSWORD).equals(password)) {
      throw new IncorrectPasswordException(password, user.getString(UserFields.USERNAME));
    }

    return user.getString(UserFields.ID);
  }
}
