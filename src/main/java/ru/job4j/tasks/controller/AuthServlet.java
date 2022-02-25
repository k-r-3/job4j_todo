package ru.job4j.tasks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.bind.v2.runtime.output.FastInfosetStreamWriterOutput;
import org.hibernate.exception.ConstraintViolationException;
import ru.job4j.tasks.models.User;
import ru.job4j.tasks.service.TodoService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/auth.html");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = GSON.fromJson(request.getReader(), User.class);
        List<User> authUser = TodoService.getInstance().getUser(user);
        if (authUser.isEmpty()) {
            response.sendError(400, "Пользователь не найден!");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", authUser.get(0));
            response.sendRedirect("/todo/");
        }

    }
}
