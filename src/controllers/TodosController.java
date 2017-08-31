package controllers;

import models.PostResponse;
import models.Todo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/todos")
public class TodosController
{
    static ArrayList<Todo> allTodos;
    public TodosController() {
        if (allTodos == null || allTodos.size() == 0) {
            allTodos = new ArrayList<Todo>();
            allTodos = util.helpers.prePopulateTodos();
        }
    }

    /* ROUTES */
    /**
     * Returns all TODOs, regardless of their "state"
     * @return a list of all TODOs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Todo> listTodos() {
        return allTodos;
    }

    /**
     * Returns all TODOs that are not complete, including those that are overdue
     * @return a list of incomplete TODOs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pending")
    public List<Todo> listActiveTodos() {
        List<Todo> result = allTodos.stream().filter(t -> t.IsCompleted == false).collect(Collectors.toList());
        return result;
    }

    /**
     * Returns all TODOs that are overdue
     * @return a list of overdue TODOs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/overdue")
    public List<Todo> listOverdueTodos() {
        List<Todo> result = allTodos.stream().filter(t -> t.IsCompleted == false &&
                t.DueDate.compareTo(new Date()) < 0).collect(Collectors.toList());
        return result;
    }

    /**
     * Returns all TODOs that are complete
     * @return a list of completed TODOs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/complete")
    public List<Todo> listCompleteTodos() {
        List<Todo> result = allTodos.stream().filter(t -> t.IsCompleted == true).collect(Collectors.toList());
        return result;
    }

    /**
     * Returns the specified TODO. If it doesn't exist, we retrun a null
     * object. Alternatively, we could return an error message and/or a 401.
     * Both are valid RESTy approaches to take, as long as the API is documented well.
     * @return a single TODO, or a null object if the ID isn't valid
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Todo getTodo(@PathParam("uuid") String value) {
        Todo result = allTodos.stream().filter(t -> t.Id.toString().equals(value)).findFirst().orElse(null);
        return result;
    }

    /**
     * Updates the specified TODO
     * @param stringUuid The ID of the TODO to be updated
     * @param tInput the TODO, in JSON format, with the values to be changed or added
     * @return A PostResponse that includes the updated TODO
     */
    @POST
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PostResponse updateTodo(@PathParam("uuid") String stringUuid, Todo tInput){
        Todo existing = allTodos.stream().filter(t -> t.Id.toString().equals(stringUuid)).findFirst().orElse(null);
        if (existing != null) {
            if (tInput.Details != null) existing.Details = tInput.Details;
            if (tInput.DueDate != null) existing.DueDate = tInput.DueDate;
            if (tInput.Task != null) existing.Task = tInput.Task;
            if (tInput.IsCompleted != null) existing.IsCompleted = tInput.IsCompleted;
            return new PostResponse("success", "TODO " + existing.Id + " has been updated.", existing);
        } else {
            return  new PostResponse("error", "TODO " + tInput + " was not found.", null);
        }
    }
    /**
     * Creates a TODO.
     * @param tInput a valid Todo object in JSON format...with or without an ID
     * @return A PostResponse that includes the new TODO
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public PostResponse addTodo (Todo tInput){
        try {
            if (tInput.Id != null) {
                allTodos.add(tInput);
                String result = "TODO added: " + tInput;
                return new PostResponse("success", "A new TODO has been created.", tInput);
            } else {
                Todo tNew = new Todo(tInput); // allows us to pass in a TODO that doesn't have an ID yet.
                allTodos.add(tNew);
                String result = "TODO added: " + tNew;
                return new PostResponse("success", "A new TODO has been created.", tNew);
            }
        } catch (Exception ex) {
            return new PostResponse("error", ex.toString(), null);
        }
    }

    /**
     * Deletes an existing TODO
     * @param stringUuid The UUID of the TODO to delete.
     * @return a PostResponse
     */
    @DELETE
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public PostResponse deleteTodo(@PathParam("uuid") String stringUuid){
        Todo existing = allTodos.stream().filter(t -> t.Id.toString().equals(stringUuid)).findFirst().orElse(null);
        if (existing != null) {
            try {
                allTodos.remove(existing);
                return new PostResponse("success", "The TODO with ID " + stringUuid + " was deleted.", null);
            } catch (Exception ex) {
                return new PostResponse("error", ex.toString(), null);
            }
        } else {
            return new PostResponse("error","No TODO was found with an ID of " + stringUuid + ".", null);
        }
    }
}