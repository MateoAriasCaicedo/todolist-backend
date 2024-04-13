package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(
      CategoryRepository categoryRepository, ApplicationEventPublisher eventPublisher) {
    this.categoryRepository = categoryRepository;
  }

  public void createUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, EmptyCategoryException {

    if (category.isEmpty()) throw new EmptyCategoryException(category);

    categoryRepository.createUserCategory(userID, category);
  }

  public void deleteUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException {
    categoryRepository.deleteUserCategory(userID, category);
  }

  public List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {
    return categoryRepository.getAllUserCategories(userID);
  }
}
