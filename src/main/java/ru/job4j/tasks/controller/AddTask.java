package ru.job4j.tasks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.models.User;
import ru.job4j.tasks.service.TodoService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AddTask extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = GSON.fromJson(req.getReader(), Task.class);
        task.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        TodoService.getInstance().createTask(task, user);
    }
}
