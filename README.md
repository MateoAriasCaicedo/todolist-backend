# Descripción del proyecto

Este proyecto es un backend construido en Java que proporciona una API para una aplicación de lista de tareas. Permite a los usuarios registrarse, iniciar sesión, crear, actualizar, eliminar y ver tareas, así como administrar categorías personalizadas para organizar las tareas.

## Características

- Registro e inicio de sesión de usuarios
- Creación, actualización, eliminación y visualización de tareas
- Asignación de categorías personalizadas a las tareas
- Filtrado de tareas por estado (completadas, vencidas, de hoy)
- Validación de datos de entrada
- Persistencia de datos en MongoDB

## Tecnologías utilizadas

- Java 17
- MongoDB
- MongoDB Java Driver
- Lombok

## Requisitos

- Java 17 o superior
- MongoDB (local o remoto)

## Instrucciones de instalación

### Configuración

1. Clona el repositorio: `git clone https://github.com/tu-usuario/todo-list-backend.git`
2. Navega al directorio del proyecto: `cd todo-list-backend`
3. Configura la URL de la base de datos MongoDB en `src/main/java/com/codecrafters/todolistbackend/database/DatabaseConfiguration.java`
4. Compila el proyecto: `./mvnw clean package`
5. Ejecuta la aplicación: `java -jar target/todo-list-backend.jar`

## Uso

Una vez que la aplicación esté en ejecución, podrás interactuar con ella a través de su API. Puedes utilizar herramientas como Postman, Insomnia o curl para enviar solicitudes HTTP.

## Créditos

- Javier Diaz Suarez
- Mateo Arias Caicedo
- Angie Alejandra Fuquen Montañez
- Juan Pavas Agudelo
