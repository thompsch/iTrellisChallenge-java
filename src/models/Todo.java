package models;

import java.util.*;

public class Todo {
    public UUID Id;
    public String Task;
    public Date DueDate;
    public Boolean IsCompleted;
    public String Details;

    public Todo() {}

    public Todo(String task, Date dueDate){
        this.Id = UUID.randomUUID(); // if this were "real", we'd have the DB assign Ids, probably.
        this.Task = task;
        this.DueDate = dueDate;
        this.IsCompleted = false; //assumes: we don't create a task that's already complete.
    }

    /**
     * Allows us to create a new TODO object from a JSON representation that doesn't have an ID yet.
     * @param t A valid Todo object
     */
    public Todo(Todo t) {
        this.Id = UUID.randomUUID();
        this.Task = t.Task;
        this.IsCompleted = t.IsCompleted;
        this.DueDate = t.DueDate;
        this.Details = t.Details;
    }
}