package ru.job4j.tasks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.service.ShowUndoneTasks;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DisplayUndone extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Task> tasks = ShowUndoneTasks.showAll();
        System.out.println(tasks);
        OutputStream out = resp.getOutputStream();
        String json = GSON.toJson(tasks);
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
