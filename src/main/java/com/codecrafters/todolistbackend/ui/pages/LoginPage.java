package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.users.IncorrectPasswordException;
import com.codecrafters.todolistbackend.domain.users.UserController;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.codecrafters.todolistbackend.ui.input.InputReader;
import java.util.Optional;
import org.bson.types.ObjectId;

public class LoginPage implements Page {

  private final UserController userController = new UserController();

  private static void printLoginPageMessage() {
    System.out.println("Inicia sesión en tu cuenta");
  }

  @Override
  public PageExitCode render() {
    printLoginPageMessage();

    while (true) {
      String userName = InputReader.readWithMessage("Nombre de usuario: ");
      String password = InputReader.readWithMessage("Contraseña: ");

      Optional<ObjectId> loginID = loginUser(userName, password);

      if (loginID.isPresent()) {
        return new MainPage(loginID.get()).render();
      }
    }
  }

  private Optional<ObjectId> loginUser(String username, String password) {
    try {
      String userID = userController.singInUser(username, password);
      return Optional.of(new ObjectId(userID));
    } catch (UserDoesNotExistsException exception) {
      System.out.println("El nombre de usuario no existe, por favor elige uno correcto");
      return Optional.empty();
    } catch (IncorrectPasswordException exception) {
      System.out.println("La contraseña es incorrecta, por favor inténtalo de nuevo");
      return Optional.empty();
    }
  }
}
