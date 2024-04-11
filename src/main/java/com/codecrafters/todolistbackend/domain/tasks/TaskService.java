package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.exceptions.InvalidDateException;
import com.codecrafters.todolistbackend.exceptions.InvalidTitleException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

@Service
class TaskService {

  private final TaskRepository taskRepository;

  private final ApplicationEventMulticaster eventPublisher;

  public TaskService(TaskRepository taskRepository, ApplicationEventMulticaster eventPublisher) {
    this.taskRepository = taskRepository;
    this.eventPublisher = eventPublisher;
  }

  void addUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {
    LocalDate deadLine = LocalDate.parse(task.dueDate());
    if (!task.dueDate().matches(TaskValidationRegex.DATE_VALIDATION.validationString) || deadLine.isBefore(
        LocalDate.now())) {
      throw new InvalidDateException(task.dueDate());
    } else if (task.title().isEmpty()) {
      throw new InvalidTitleException(task.title());
    }

    taskRepository.createUserTask(userID, task);
  }

  void deleteUserTask(ObjectId userID, ObjectId taskID) throws UserDoesNotExistsException {
    taskRepository.deleteUserTask(userID, taskID);
  }

  void completeUserTask(ObjectId userID, ObjectId taskID) throws UserDoesNotExistsException {
    taskRepository.updateField(userID, taskID, TaskFields.COMPLETED, Boolean.TRUE);
  }

  List<Task> getAllUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    return taskRepository.findAllUserTasks(userID);
  }
}
