package com.codecrafters.todolistbackend.domain.users;

import static com.mongodb.client.model.Filters.eq;

import com.codecrafters.todolistbackend.db.collections.CollectionsProvider;
import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.codecrafters.todolistbackend.db.filters.DBFilters;
import com.codecrafters.todolistbackend.db.indexes.UsersIndex;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.MongoWriteException;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
class UserRepository {

  private final CollectionsProvider collectionsProvider;

  String createUser(UserCreationDTO user) throws UsernameAlreadyExists, EmailAlreadyExists {
    log.info("Creating a user with userName: {}", user.userName());

    var userDocument =
        user.asMongoDocument()
            .append(UserFields.TASKS, List.of())
            .append(UserFields.CATEGORIES, List.of());

    try {
      var insertedUser = collectionsProvider.usersCollection().insertOne(userDocument);
      return insertedUser.getInsertedId().asObjectId().getValue().toHexString();

    } catch (MongoWriteException indexViolatedException) {
      Optional<UsersIndex> violatedIndex =
          UsersIndex.findViolatedIndex(indexViolatedException.getMessage());

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
        collectionsProvider.usersCollection().find(DBFilters.equalUsernameFilter(username)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(username);
    }

    if (!user.getString(UserFields.PASSWORD).equals(password)) {
      throw new IncorrectPasswordException(password, username);
    }

    return user.getObjectId(UserFields.ID).toHexString();
  }
}
