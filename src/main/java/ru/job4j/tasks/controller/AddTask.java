package ru.job4j.tasks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.service.CreateTask;
import ru.job4j.tasks.service.ShowDoneTasks;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AddTask extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = GSON.fromJson(req.getReader(), Task.class);
        task.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        CreateTask.createTask(task);
    }
}
