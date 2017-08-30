package controllers;

import models.Todo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/message")
public class todo
{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMsg()
    {
        return "Hello World !! - Jersey 2";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json")
    public Todo getJson(){
        Todo t = new Todo("do something, yo");
        return t;
    }
}

