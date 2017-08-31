package controllers;

import models.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class TodosControllerTest {

    TodosController controller = new TodosController();

    @Test
    public void listTodosTest() throws Exception {
        List<Todo> alLTodos = controller.listTodos();
        assertEquals(5, alLTodos.size());
    }

    @Test
    public void listActiveTodosTest() throws Exception {
        List<Todo> todos = controller.listActiveTodos();
        assertEquals(4, todos.size());
    }

    @Test
    public void listOverdueTodosTest() throws Exception {
        List<Todo> todos = controller.listOverdueTodos();
        assertEquals(2, todos.size());
    }

    @Test
    public void listCompleteTodosTest() throws Exception {
        List<Todo> todos = controller.listCompleteTodos();
        assertEquals(1, todos.size());
        assertEquals("Do that early bird and worm thing.", todos.get(0).Task);
    }

    @Test
    public void getTodoTest() throws Exception {
        List<Todo> allTodos = controller.listTodos();
        UUID firstTodoId = allTodos.get(0).Id;
        Todo firstTodo = controller.getTodo(firstTodoId.toString());
        assertEquals("Buy a loaf of bread. If they have eggs, buy a dozen.", firstTodo.Task);
    }

    @Test
    public void updateTodoTest() throws Exception {
        Todo tToUpdate = controller.allTodos.get(3);
        tToUpdate.Details = "ohhai I'm the details";
        tToUpdate.Task = "ohhai I'm an updated task description.";
        controller.updateTodo(tToUpdate.Id.toString(), tToUpdate);
        Todo tUpdated = controller.getTodo(tToUpdate.Id.toString());
        assertEquals("ohhai I'm an updated task description.", tUpdated.Task);
        assertEquals("ohhai I'm the details", tUpdated.Details);
    }

    /**
     * Because we can't guarentee the test order, we have to add and remove a todo
     * in the same unit test, or else the counts above may be off. :(
     */
    @Test
    public void addTodoAndDeleteTest() throws Exception {
        Todo tNew = new Todo("Add unit tests.", new Date());
        tNew.Details = "Really, you should create the tests first, ya know...";
        PostResponse result = controller.addTodo(tNew);
        assertEquals("success", result.responseType);
        assertEquals("A new TODO has been created.", result.Message);
        controller.deleteTodo(tNew.Id.toString());
        Todo tShouldBeNull = controller.getTodo(tNew.Id.toString());
        assertNull(tShouldBeNull);
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(TodosController.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
