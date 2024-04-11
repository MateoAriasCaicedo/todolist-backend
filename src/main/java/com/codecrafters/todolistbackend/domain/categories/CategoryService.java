package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.events.CategoryDeletedEvent;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final ApplicationEventPublisher eventPublisher;

  public CategoryService(CategoryRepository categoryRepository, ApplicationEventPublisher eventPublisher) {
    this.categoryRepository = categoryRepository;
    this.eventPublisher = eventPublisher;
  }

  void createUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, EmptyCategoryException {

    if (category.isEmpty()) throw new EmptyCategoryException(category);

    categoryRepository.createUserCategory(userID, category);
    eventPublisher.publishEvent(new CategoryDeletedEvent(this));
  }

  void deleteUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    categoryRepository.deleteUserCategory(userID, category);
  }
}
