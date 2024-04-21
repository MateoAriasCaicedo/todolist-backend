package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.database.DBIndex;
import com.codecrafters.todolistbackend.database.FiltersProvider;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.MongoWriteException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
class UserRepository {

  String createUser(UserCreationDTO user) throws UsernameAlreadyExists, EmailAlreadyExists {
    log.info("Creating a user with userName: {}", user.userName());

    var userDocument =
        user.asMongoDocument()
            .append(UserFields.TASKS, List.of())
            .append(UserFields.CATEGORIES, List.of());

    try {
      var insertedUser =
          com.codecrafters.todolistbackend.database.CollectionsProvider.users()
              .insertOne(userDocument);
      return insertedUser.getInsertedId().asObjectId().getValue().toHexString();

    } catch (MongoWriteException exception) {
      Optional<DBIndex> violatedIndex = DBIndex.findViolatedIndex(exception);

      if (violatedIndex.isEmpty()) {
        throw new RuntimeException("Unexpected database error.");
      }

      switch (violatedIndex.get()) {
        case UNIQUE_USERNAME -> throw new UsernameAlreadyExists();
        case UNIQUE_EMAIL -> throw new EmailAlreadyExists();
        default -> throw new RuntimeException("Unexpected violation");
      }
    }
  }

  String findUser(String username, String password) throws UserDoesNotExistsException {
    log.info("logging in user with username: {} ", username);

    Document user =
        com.codecrafters.todolistbackend.database.CollectionsProvider.users()
            .find(FiltersProvider.equalUsernameFilter(username))
            .first();

    if (user == null) {
      throw new UserDoesNotExistsException(username);
    }

    if (!user.getString(UserFields.PASSWORD).equals(password)) {
      throw new IncorrectPasswordException(password, username);
    }

    return user.getObjectId(UserFields.ID).toHexString();
  }
}
