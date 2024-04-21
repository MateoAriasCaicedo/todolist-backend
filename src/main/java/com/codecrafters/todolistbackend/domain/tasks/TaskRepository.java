package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.database.CollectionsProvider;
import com.codecrafters.todolistbackend.database.FiltersProvider;
import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.TaskDoesNotExistsException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

class TaskRepository {

  private static Optional<Document> userDocument(ObjectId userID) {
    Document user = CollectionsProvider.users().find(FiltersProvider.equalUserID(userID)).first();

    if (user == null) {
      return Optional.empty();
    }

    return Optional.of(user);
  }

  @NotNull
  private static Optional<Document> taskDocument(@NotNull Document user, @NotNull ObjectId taskID) {
    return user.getList(UserFields.TASKS, Document.class).stream()
        .filter(taskDocument -> taskDocument.getObjectId(TaskFields.ID).equals(taskID))
        .findFirst();
  }

  <T> void updateField(ObjectId userID, ObjectId taskID, String field, T value)
      throws UserDoesNotExistsException {

    Optional<Document> user = userDocument(userID);

    if (user.isEmpty()) {
      throw new UserDoesNotExistsException(taskID);
    }

    var task = taskDocument(user.get(), taskID);

    if (task.isEmpty()) {
      throw new TaskDoesNotExistsException(taskID);
    }

    var taskDocument = task.get();
    taskDocument.remove(field);
    taskDocument.append(field, value);

    recreateUserTask(userID, taskDocument);
  }

  private void recreateUserTask(ObjectId userID, Document taskDocument) {
    var taskID = taskDocument.getObjectId(TaskFields.ID);
    deleteUserTask(userID, taskID);
    createUserTask(userID, TaskCreationDTO.fromDocument(taskDocument), taskID);
  }

  private UpdateResult insertUserTask(ObjectId userID, Document taskDocument) {
    return CollectionsProvider.users()
        .updateOne(
            FiltersProvider.equalUserID(userID), Updates.push(UserFields.TASKS, taskDocument));
  }

  String createUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {
    var taskDocument = task.asMongoDocument();
    var createdTasks = insertUserTask(userID, task.asMongoDocument());

    if (createdTasks.getModifiedCount() < 1) {
      throw new UserDoesNotExistsException(userID);
    }

    return taskDocument.getObjectId(TaskFields.ID).toHexString();
  }

  String createUserTask(ObjectId userID, TaskCreationDTO task, ObjectId taskID)
      throws UserDoesNotExistsException {
    var createdTasks = insertUserTask(userID, task.asMongoDocument().append(TaskFields.ID, taskID));

    if (createdTasks.getModifiedCount() < 1) {
      throw new UserDoesNotExistsException(userID);
    }

    return taskID.toHexString();
  }

  void deleteUserTask(ObjectId userID, ObjectId taskID) {
    var updatedTasks =
        CollectionsProvider.users()
            .updateOne(
                FiltersProvider.equalTaskID(userID),
                Updates.pull(UserFields.TASKS, new Document(TaskFields.ID, taskID)));

    if (updatedTasks.getModifiedCount() < 1) {
      throw new UserDoesNotExistsException(userID);
    }
  }

  List<Task> findAllUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    var user = CollectionsProvider.users().find(FiltersProvider.equalTaskID(userID)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(userID);
    }

    var tasks = new LinkedList<Task>();

    for (Object document : user.get(UserFields.TASKS, List.class)) {
      tasks.add(Task.fromDocument((Document) document));
    }

    return tasks;
  }

  List<Task> findCompleteUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    var user = CollectionsProvider.users().find(FiltersProvider.equalTaskID(userID)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(userID);
    }

    var tasks = new LinkedList<Task>();

    for (Object document : user.get(UserFields.TASKS, List.class)) {
      if (Task.fromDocument((Document) document).completed()) {
        tasks.add(Task.fromDocument((Document) document));
      }
    }

    return tasks;
  }
}
