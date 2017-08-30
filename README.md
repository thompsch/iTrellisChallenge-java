# iTrellisChallenge-java

A Java implementation of a simple TODO list RESTful API. This project uses JAX-RS2 to handle the basic Web Service structure, 
and Jersey for the JSON support. It can be deployed locally using GlassFish 4.1.

### User Stories and API Endpoints

| User Story                                     | METHOD             | URL Path   | Notes |
|-------------------------------------------|-------------------|-------------| ----- |
| As a user, I can see all the TODOs on the list in an overview.| GET | /todos  |  |
| As a user, I can add a TODO to the list. | POST | /todos |  |   |
| As a user, I can drill into a TODO to see more information about the TODO.| GET |  /todos/{id} | This endpoint returns the complete TODO, including the Details field. It is small enough that returning it as part of the complete object makes sense. |
|As a user, I can delete a TODO.| DELETE |  /todos/{id} ||
|As a user, I can mark a TODO as completed. | POST |  /todos/{id} | I expanded this a bit to be "As a user, I can modify an existing TODO." In a good UI, of course, we would provide a checkbox or the like to mark something as complete, but behind the scenes, the same API endpoint can (should?) be used for all updates to an existing TODO. Note, too, that this POST and the POST to add a new TODO differ in their path. If your POST URL includes the ID, we know you're updating. If not, we assume it's a new TODO. |
|As a user, when I see all the TODOs in the overview, if today's date is past the TODO's deadline, highlight it.| n/a | n/a | This is a UI/UX implementation. That being said, see the next three bonus user stories...
| -- BONUS user story #1: As an API consumer, I can get a list of overdue TODOs | GET | /todos/overdue |
| -- BONUS user story #2: As an API consumer, I can get a list of incomplete TODOs | GET | /todos/pending |
| -- BONUS user story #3: As an API consumer, I can get a list of completed TODOs | GET | /todos/complete |

### Code Structure
- All routes are defined in /src/controllers/TodosController.
- The TODO model, which is used in all routes, is defined in /src/models/Todo.
- Because this is all in-memory and not using a backing store, there is a method in /src/util/helpers.java that pre-populates the TODO collection.

### Deploying

### Testing

