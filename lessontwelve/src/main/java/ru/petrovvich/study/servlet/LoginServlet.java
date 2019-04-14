package ru.petrovvich.study.servlet;


import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final UserService userService;

    public LoginServlet() {
        this.templateProcessor = new TemplateProcessor();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage("login.html", parameters));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("login");
        String password = req.getParameter("password");

        UserDataSet existUser = userService.findByName(name);

        resp.setContentType("text/html;charset=utf-8");
        if (existUser == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            resp.setStatus(200);
        } else if (!existUser.getPassword().equals(password)) {
            resp.setStatus(401);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("login", name);
            resp.sendRedirect(req.getContextPath() + "/home");
            resp.setStatus(200);
        }
    }
}
