package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.domain.validations.TodoValidator;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TaskService {

  private final TaskRepository taskRepository;

  private final TodoValidator todoValidator;

  String addUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {

    if (!task.dueDate().matches(TaskValidationRegex.DATE_VALIDATION)
        || LocalDate.parse(task.dueDate()).isBefore(LocalDate.now())) {
      throw new InvalidDateException(task.dueDate());
    }

    if (task.title().isEmpty()) {
      throw new InvalidTitleException(task.title());
    }

    if (!todoValidator.userHasCategory(userID, task.category())) {
      throw new InvalidTaskCategory(task.category());
    }

    return taskRepository.createUserTask(userID, task);
  }

  void deleteUserTask(ObjectId userID, ObjectId taskID) throws UserDoesNotExistsException {
    taskRepository.deleteUserTask(userID, taskID);
  }

  void completeUserTask(ObjectId userID, ObjectId taskID) throws UserDoesNotExistsException {
    taskRepository.updateField(userID, taskID, TaskFields.COMPLETED, Boolean.TRUE);
  }

  void updateTitleUserTask(ObjectId userID, ObjectId taskID, String title)
      throws UserDoesNotExistsException {

    if (title.isEmpty()) {
      throw new InvalidTitleException(title);
    }

    taskRepository.updateField(userID, taskID, TaskFields.TITLE, title);
  }

  void updateDescriptionUserTask(ObjectId userID, ObjectId taskID, String description)
      throws UserDoesNotExistsException {
    taskRepository.updateField(userID, taskID, TaskFields.DESCRIPTION, description);
  }

  void updateDueDateUserTask(ObjectId userID, ObjectId taskID, String dueDate)
      throws UserDoesNotExistsException {

    if (!dueDate.matches(TaskValidationRegex.DATE_VALIDATION)
        || LocalDate.parse(dueDate).isBefore(LocalDate.now())) {
      throw new InvalidDateException(dueDate);
    }

    taskRepository.updateField(userID, taskID, TaskFields.DUE_DATE, dueDate);
  }

  void updateCategoryUserTask(ObjectId userID, ObjectId taskID, String category)
      throws UserDoesNotExistsException {

    if (!todoValidator.userHasCategory(userID, category)) {
      throw new InvalidTaskCategory(category);
    }

    taskRepository.updateField(userID, taskID, TaskFields.DUE_DATE, category);
  }

  List<Task> getAllUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    return taskRepository.findAllUserTasks(userID);
  }
}
