package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.events.TaskCreatedEvent;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
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
    taskRepository.createUserTask(userID, task);
    eventPublisher.multicastEvent(new TaskCreatedEvent(this));
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
