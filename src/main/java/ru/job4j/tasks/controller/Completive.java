package ru.job4j.tasks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.service.TodoService;

import javax.servlet.http.*;
import java.io.IOException;

public class Completive extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Task task = GSON.fromJson(request.getReader(), Task.class);
        boolean status = false;
        if (!task.isDone()) {
            status = true;
        }
        TodoService.getInstance().changeStatus(String.valueOf(task.getId()), status);
    }
}
