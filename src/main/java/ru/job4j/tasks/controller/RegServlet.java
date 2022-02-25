package ru.job4j.tasks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.exception.ConstraintViolationException;
import ru.job4j.tasks.models.User;
import ru.job4j.tasks.service.TodoService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        ServletContext context = request.getServletContext();
//        RequestDispatcher dispatcher = context
//                .getRequestDispatcher("/auth.html");
//        dispatcher.forward(request, response);
        response.sendRedirect("/todo/auth");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = GSON.fromJson(request.getReader(), User.class);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        try {
            TodoService.getInstance().addUser(user);
        } catch (ConstraintViolationException cve) {
            response.sendError(400, cve.getMessage());
            return;
        }
        this.doGet(request, response);
    }
}
