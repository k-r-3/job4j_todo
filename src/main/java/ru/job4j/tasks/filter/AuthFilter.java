package ru.job4j.tasks.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("d");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession();
        System.out.println(session);
        if (uri.endsWith("auth")
                || uri.endsWith("reg")
                || uri.endsWith("auth.html")
                || uri.endsWith("reg.html")
        ) {
            chain.doFilter(request, response);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            ServletContext context = req.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/auth.html");
            dispatcher.forward(req, resp);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
