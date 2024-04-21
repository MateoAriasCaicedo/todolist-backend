package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.InvalidDateFormatException;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.InvalidTaskCategory;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.EmptyTaskTitleException;
import com.codecrafters.todolistbackend.domain.validations.TodoValidator;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;

class TaskService {

  private final TaskRepository taskRepository;

  private final TodoValidator todoValidator;

  public TaskService(TaskRepository taskRepository, TodoValidator todoValidator) {
    this.taskRepository = taskRepository;
    this.todoValidator = todoValidator;
  }

  String addUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {

    if (!task.dueDate().matches(TaskValidationRegex.DATE_FORMAT_VALIDATION)
        || LocalDate.parse(task.dueDate()).isBefore(LocalDate.now())) {
      throw new InvalidDateFormatException(task.dueDate());
    }

    if (task.title().isEmpty()) {
      throw new EmptyTaskTitleException();
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
      throw new EmptyTaskTitleException();
    }

    taskRepository.updateField(userID, taskID, TaskFields.TITLE, title);
  }

  void updateDescriptionUserTask(ObjectId userID, ObjectId taskID, String description)
      throws UserDoesNotExistsException {
    taskRepository.updateField(userID, taskID, TaskFields.DESCRIPTION, description);
  }

  void updateDueDateUserTask(ObjectId userID, ObjectId taskID, String dueDate)
      throws UserDoesNotExistsException {

    if (!dueDate.matches(TaskValidationRegex.DATE_FORMAT_VALIDATION)
        || LocalDate.parse(dueDate).isBefore(LocalDate.now())) {
      throw new InvalidDateFormatException(dueDate);
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

  List<Task> getCompleteTasks(ObjectId userID) throws UserDoesNotExistsException {
    return taskRepository.findCompleteUserTasks(userID);
  }

  List<Task> getTodayTasks(ObjectId userID) throws UserDoesNotExistsException {
    return taskRepository.getTodayTasks(userID);
  }

  List<Task> getOverdueTasks(ObjectId userID) {
    return taskRepository.getOverdueTasks(userID);
  }

  List<Task> getTasksByCategory(ObjectId userID, String category) {
    return taskRepository.getTasksByCategory(userID, category);
  }
}
