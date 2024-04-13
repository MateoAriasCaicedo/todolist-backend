package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.domain.validations.TodoValidator;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.util.List;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

  private final TodoValidator todoValidator;

  private final CategoryRepository categoryRepository;

  public void createUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, EmptyCategoryException {

    if (category.isEmpty()) throw new EmptyCategoryException(category);

    categoryRepository.createUserCategory(userID, category);
  }

  public void deleteUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException {

    if (!todoValidator.userHasCategory(userID, category)) {
      throw new CategoryDoesNotExistsException();
    }

    categoryRepository.deleteUserCategory(userID, category);
  }

  public List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {
    return categoryRepository.getAllUserCategories(userID);
  }
}
