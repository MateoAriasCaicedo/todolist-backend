package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.users.*;
import com.codecrafters.todolistbackend.ui.input.InputReader;
import java.util.Optional;
import org.bson.types.ObjectId;

public class SignupPage implements Page {

  private final UserController userController = new UserController();

  private static void printSingUpMessage() {
    System.out.println("Registrarse en la aplicación.");
  }

  @Override
  public PageExitCode render() {
    printSingUpMessage();

    while (true) {
      String firstName = InputReader.readWithMessage("Nombres:");
      String lastName = InputReader.readWithMessage("Apellidos");
      String userName = InputReader.readWithMessage("Nombre de usuario");
      String email = InputReader.readWithMessage("Correo electrónico:");
      String password = InputReader.readWithMessage("Contraseña:");

      UserCreationDTO user = new UserCreationDTO(firstName, lastName, userName, email, password);
      Optional<ObjectId> createdUserID = singUpUser(user);

      if (createdUserID.isPresent()) {
        return new MainPage(createdUserID.get()).render();
      }
    }
  }

  private Optional<ObjectId> singUpUser(UserCreationDTO user) {
    try {
      String userID = userController.signUpUser(user);
      return Optional.of(new ObjectId(userID));
    } catch (InvalidPasswordException exception) {
      System.out.println("La contraseña es inválida, por favor inténtalo de nuevo");
      return Optional.empty();
    } catch (InvalidEmailException exception) {
      System.out.println("El correo electrónico es inválido, por favor inténtalo de nuevo");
      return Optional.empty();
    } catch (UsernameAlreadyExists exception) {
      System.out.println("El nombre de usuario ya existe, por favor elige otro");
      return Optional.empty();
    } catch (EmailAlreadyExists exception) {
      System.out.println("El correo electrónico ya existe, por favor elige otro");
      return Optional.empty();
    }
  }
}
