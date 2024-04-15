package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.database.CollectionsProvider;
import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.codecrafters.todolistbackend.database.FiltersProvider;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.client.model.Updates;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
class TaskRepository {

  private final CollectionsProvider collectionsProvider;

  <T> void updateField(ObjectId userID, ObjectId taskID, String field, T value)
      throws UserDoesNotExistsException {
    var user = collectionsProvider.users().find(FiltersProvider.equalUserID(userID)).first();

    if (user == null) throw new UserDoesNotExistsException(taskID);

    var task =
        user.getList(UserFields.TASKS, Document.class).stream()
            .filter(taskDocument -> taskDocument.getObjectId(TaskFields.ID).equals(taskID))
            .findFirst();

    if (task.isEmpty()) throw new TaskDoesNotExistsException(taskID);

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
    return collectionsProvider
        .users()
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
        collectionsProvider
            .users()
            .updateOne(
                FiltersProvider.equalTaskID(userID),
                Updates.pull(UserFields.TASKS, new Document(TaskFields.ID, taskID)));

    if (updatedTasks.getModifiedCount() < 1) {
      throw new UserDoesNotExistsException(userID);
    }
  }

  List<Task> findAllUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    var user = collectionsProvider.users().find(FiltersProvider.equalTaskID(userID)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(userID);
    }

    var tasks = new LinkedList<Task>();

    for (Object document : user.get(UserFields.TASKS, List.class)) {
      tasks.add(Task.fromDocument((Document) document));
    }

    return tasks;
  }
}
