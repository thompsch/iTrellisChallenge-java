package util;

import models.Todo;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class helpers {

    // HACK: in the real world, we'd use a backing store
    // to hold our TODOs. Since this data isn't relational,
    // a NoSql solution probably makes the most sense--cheap and easy.
    // I've abstracted it to this helper class so it would be easy to
    // switch this logic without affecting the controllers & models.
    public static ArrayList<Todo> prePopulateTodos() {
        ArrayList<Todo> result = new ArrayList<Todo>();
        Todo todoWithDetails = new Todo("Defibrillate the master oscillator", parseDate("2017-09-04"));
        todoWithDetails.Details = "Insert tab A into slot B";

        Todo overDueTodo = new Todo("Procrastinate", parseDate("2017-08-01"));
        overDueTodo.IsCompleted = false;

        Todo completed = new Todo("Do that early bird and worm thing.", parseDate("2017-10-01"));
        completed.IsCompleted = true;

        result.add(new Todo("Buy a loaf of bread. If they have eggs, buy a dozen.", parseDate("2017-09-15")));
        result.add(new Todo("Complete iTrellis coding challenge.", parseDate("2017-08-30")));
        result.add(overDueTodo);
        result.add(todoWithDetails);
        result.add(completed);
        return result;
    }

    //A simple wrapper for parsing a string to a date and making sure it's valid
    private static Date parseDate(String dateAsString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateAsString);
        }
        catch (ParseException e){
            return null;
        }
    }
}
