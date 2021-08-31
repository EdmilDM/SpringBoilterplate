# Boilerplate Spring Boot project

This is a boilerplate project to kickstart my Spring Boot projects with a lot of the functionality I use and need, this serves as a base project to expand my REST APIs from.

Comes with basic models, repositories, controllers and tests as examples on how to work with them. 

## Technologies

* Java with Spring Boot
* H2 as embedded database 
* Maven for dependency management
* Docker for containerization
* OpenAPI for API documentation
* Spring Security for authentication combined with JWT
* HATEOAS for url management of return objects in the responses.
* Junit for unit testing, and Mockito for mocking.

## Running the project

Since this is a maven based project, just use ```./mvnw install```, and run the application with your IDE of choice. Alternatively, you can build the project and execute the generated jar or use docker (docker section below).

## Structure

The project is structured the following way:

### ```src/main/java/com/edmil/boilerplate``` :

#### Assembler

The assembler package contains the HATEOAS assembler classes, which pretty much just give our models in our response urls, to reduce coupling by not having to hard code the urls in the front end and reduce logic in the front end.

##### Representation

Inside the assembler package lies representation, which gives us DTO to convert our JPA objects into HATEOAS objects to return to the client, this is done in order to be able to add links to the list inside DanceOff return objects.

#### Controller

The controller package handles the controller layer, there are 3 controllers, one for each model.

#### Database

A single file that loads the database with some basic data to be able to use the API, purely for convenience. It's a Configuration class with a couple of CommandLineRunner that are run as soon as the project starts to create 2 robots and 1 user.

#### Exception

Exception management package. The APIExceptionHandler is a middleware that intercepts all exceptions sent back to the client and builds an ExceptionReturnObject appropriate for each exception to send back to the user. Only intercepts controllers, will not work for JWT authentication since it's done with a filter.

##### CustomExceptions

There's 3 custom exceptions in the aptly named customexceptions inside exception, AuthenticationFailedException for when user tries to validate with invalid credentials, NotFoundException for when searching for an object whose ID doesn't exist and UserAlreadyExists for when the username is taken in signup.

#### Model

This is where our object models reside. Currently 4, Battle, DanceOff, Robot and User.

#### Repository

Here we store our data access objects, all implement JPARepository with comes with a lot of predefined and useful methods for accessing our data.

#### Security

The security package, SecurityConfiguration sets up the security configuration for the whole application with Spring Security, and JWTAuthenticationFilter is a filter used for intersecting restricted endpoints and seeing if the correct authentication via JWT has been sent, which can be gotten on the /login endpoint.

#### Service

The service layer, the services that provide functionality to our controllers to abstract the business logic away from the controllers to keep them as tidy as possible. 

#### BoilerplateApplication

The entrypoint of our application.

### ```src/main/java/resources``` :

#### application.properties

Configuration file for Spring Boot, has properties like database information, dialect for JPA, context path for API versioning (/api/v1) and OpenAPI url.

### ```src/tests``` :

Folder where our tests reside. To run tests run ```./mvnw test``` in project root.

## Endpoints

The endpoints are defined in the controllers, they are as follows (they are all preceded by /api/v1 for API versioning defined in ```server.servlet.contextPath=/api/v1/``` in ```application.properties```)

* ```GET /api/v1/robots```
* ```POST /api/v1/robots```
* ```GET /api/v1/robots/{id}```
* ```GET /api/v1/danceoffs```
* ```POST /api/v1/danceoffs``` **protected**
* ```POST /api/v1/login```
* ```POST /api/v1/signup```

## Database

For this project, we are using H2, a lightweight in-memory Java-based database. It comes in 2 flavors, embedded and server. We are using the embedded version, making it super easy to configure and not requiring setting up a database server. Using other databases or H2 in server mode won't prove much of a problem because of the use of ORMs, they work under the hood to make our application database agnostic, and it's just a matter of changing the ```application.properties``` file to provide the new database information and dialect.

## Documentation

Documentation has been done with OpenAPI, it automatically makes documentation based on your models and controllers, it can be found in the URL in the variable ```springdoc.api-docs.path``` defined in the ```application.properties``` file located in ```src/main/java/resources```. Doing a simple GET request will return a JSON formatted documentation. Swagger is also included, its url configured in the variable ```springdoc.swagger-ui.path``` in said file.

## Authentication

The project utilizes Json Web Tokens based authorization with spring security. By default, spring security restricts access to all endpoints in the API, but you can configure a whitelist of URLs that will be open and adding them to the ```SecurityConfiguration``` class in the ```security``` package. The only protected endpoint is POST /danceoffs, as specified by the instructions (it was tagged as optional), all other endpoints used in this API are open. In order to use the secure endpoint, first a POST request must be made to the /login endpoint with a valid user (by default, there is a user created with username: admin and password: password) which in turn will return a JWT token that must be added to an Authorization header in the request, like this ```Token eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJyb2JvdEFQSSIsInN1YiI6ImVkbWlsIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYyODUyNjE0OSwiZXhwIjoxNjI4NTI2NTQ5fQ.IGhQgx9QmBKCdSIp7pQ0auuOAankqckIx10Fi_sXFq0TcB3L8g4FqZ5aMO3pjegZj168q50NJEUqAaw2-Kc5Cg```. The validation of the token on the protected endpoint is done in the ```JWTAuthorizationFilter```.

## Validation

Validation is done in the models themselves with their respective annotations. Most validations are basic, checking for not null values, the notable ones are that avatar must be a valid URL in the Robot model and the Battle list for the DanceOff must be of size 5.

## Containerization

For containerization Docker is used, building the image process is specified in the ```Dockerfile``` located in the project's root. It is a simple file that loads a jdk16 image, creates a /boilerplate directory, copies the built jar from your local pc and executes it to run the API. In order to get the jar to be used for the creation of the image, one must run ```./mvnw clean install``` to get the jar in the ```target/``` folder, otherwise when the image is created and then used, it won't find the jar to execute. Alternatively, I have added comments in the Dockerfile a way to also build and create the jar from inside the image, but it takes some extra time. Since there are no external dependencies for the project like a database server (remember, we use H2 in embedded mode, which creates an in-memory database), there is no need to configure dependencies for Docker.

If you want to use docker, you must have it installed. Running this application with docker is simple, all you have to do is build the image and then run it. 

```docker build -t edmil/boilerplate .``` builds the image based on the Dockerfile of the project.

```docker run -p 8080:8080 -t edmil/boilerplate ``` runs a container with the specified image (edmil/boilerplate in this case, which we created in the last step).
