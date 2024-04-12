package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.domain.validations.TodoValidator;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
class TaskService {

  private final TaskRepository taskRepository;

  TaskService(TaskRepository taskRepository, TodoValidator todoValidator) {
    this.taskRepository = taskRepository;
  }

  void addUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {

    if (!task.dueDate().matches(TaskValidationRegex.DATE_VALIDATION)
        || LocalDate.parse(task.dueDate()).isBefore(LocalDate.now())) {
      throw new InvalidDateException(task.dueDate());
    }

    if (task.title().isEmpty()) {
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
