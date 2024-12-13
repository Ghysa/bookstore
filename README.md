# Bookstore

This is a simple Spring Boot application that contains a set of basic API's to run a bookstore.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Using the Application](#using-the-application)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Testing](#testing)
- [License](#license)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java** 17 or later installed on your machine.
- **Maven** 3.6 or later for managing dependencies and building the project. OR you can use the `mvnw` executable included in the project.
- **IDE** (e.g., IntelliJ IDEA, Eclipse) for development (optional, but recommended).

## Installation

To set up this project on your local machine:

1. Clone the repository:
   ```bash
   git clone https://github.com/ghysa/bookstore.git
   ```

2. Navigate to the project directory:
   ```bash
   cd bookstore
   ```

3. Generate the classes from the OpenAPI spec and install the dependencies using Maven:
   ```bash
   mvn clean install
   ```

## Running the Application

You can run the Spring Boot application in several ways.

### 1. Using Maven

To run the application directly using Maven, execute the following command:

```bash
mvn spring-boot:run
```

### 2. Running a JAR file

If you have built the application using `mvn clean install`, you can run the application by executing the JAR file:

```bash
java -jar target/bookstore-0.0.1-SNAPSHOT.jar
```

### 3. Using your IDE

Simply run the main method in `BookstoreApplication.java` in your favorite IDE.

Once the application is running, you can access the application on `http://localhost:8080/api/v1`.

## Using the Application

This section explains how to interact with the API generated from the OpenAPI specification. The API provides a standardized interface for communication with external services or systems.

### Base URL

The base URL for the API is:

```
http://localhost:8080/api/v1
```

Replace `localhost:8080` with your deployment URL if running in a production or staging environment.

### Authentication and User registration

The API uses **Bearer Token Authentication** for secure communication. To authenticate, include the token in the `Authorization` header of your requests.

Example header:
```
Authorization: Bearer YOUR_ACCESS_TOKEN
```

### User Registration

To register a user, use the `/api/v1/users` endpoint. Example:

**Request:**
```http
POST /api/v1/users
Content-Type: application/json
```

**Body:**
```json
{
   "email": "samghysels@capgemini.com",
   "password": "password",
   "firstname": "Sam",
   "lastname": "Ghysels"
}
```

**Response:**
```json
{
   "email": "samghysels@capgemini.com",
   "firstname": "Sam",
   "lastname": "Ghysels"
}
```

### Login and token creation

After registering a user, you can use it to log in and generate a bearer token. Example:

**Request:**
```http
POST /api/v1/login
Content-Type: application/json
```

**Body:**
```json
{
   "email": "samghysels@capgemini.com",
   "password": "password"
}
```

**Response:**
```json
{
   "access_token": "YOUR_ACCESS_TOKEN",
   "token_type": "Bearer",
   "expires_in": "600"
}
```

### OpenAPI Specification

The API endpoints are defined using the OpenAPI specification (Swagger). You can view the full API documentation and interact with the API via Swagger UI at the following URL:

```
http://localhost:8080/api/v1/swagger-ui.html
```

This Swagger UI provides interactive documentation, allowing you to test API endpoints directly from the browser.

### Example Requests

Here are a few examples of how to interact with the API:

#### 1. **GET /books/{id}**

Fetch a list of users.

**Request:**
```http
GET /api/v1/books/1004
Authorization: Bearer YOUR_ACCESS_TOKEN
```

**Response:**
```json
{
   "id": 1004,
   "title": "Lord of the Rings: The Fellowship of the Ring",
   "author": "J. R. R. Tolkien",
   "releaseDate": "1954-07-29",
   "price": 42.32
}
```

#### 2. **POST /carts**

Create a new user.

**Request:**
```http
POST /api/v1/carts
Authorization: Bearer YOUR_ACCESS_TOKEN
```

**Response:**
```json
{
   "id": 1,
   "user": "samghysels@capgemini.com",
   "items": [],
   "total": 0
}
```

### Error Handling

API responses may return errors if something goes wrong. The error response will be structured as follows:

**Example:**
```json
{
    "code": "401",
    "message": "Incorrect/missing credentials, please log in first."
}
```

Common error codes include:

- `400 Bad Request`: Invalid data or missing parameters.
- `401 Unauthorized`: Missing or invalid authentication token.
- `500 Internal Server Error`: Unexpected server-side error.


## Project Structure

The project follows the typical Spring Boot application structure:

```
bookstore/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── capgemini/
│   │   │           └── bookstore/
│   │   │               ├── config/                     # Generic Spring Boot configuration
│   │   │                   └── security/               # Security configuration
│   │   │               ├── controller/                 # REST controllers
│   │   │               ├── entity/                     # Entity classes
│   │   │               ├── error/                      # Error handling and classes
│   │   │                   └── exception/              # Custom Exception classes
│   │   │               ├── service/                    # Business logic
│   │   │                   └── mapper/                 # Mapper classes
│   │   │               ├── repository/                 # Database repositories
│   │   │               ├── util/                       # Utility classes
│   │   │               └── BookstoreApplication.java   # Main application class
│   │   └── resources/
│   │       ├── sample/                                 # Sample request responses
│   │       ├── static/                                 # Static files (OpenAPI spec)
│   │       ├── application.yaml                        # Application configuration
│   │       └── data.sql                                # Database script run during the database initialization
├── target/                                             # Build output directory
├── pom.xml                                             # Maven build configuration
└── README.md                                           # This README file
```

## Configuration

The application is configured using the `application.yaml` file located in `src/main/resources`.

## Testing

This Spring Boot application comes with built-in testing capabilities. It uses **JUnit** and **Mockito** for unit and integration tests.

### Running Tests

To run tests, use the following Maven command:

```bash
mvn test
```

### Test Coverage

To generate a test coverage report with Jacoco run the following Maven command:

```bash
mvn jacoco:report
```

You can find the generated report in the following location: `target/site/jacoco/index.html`.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.