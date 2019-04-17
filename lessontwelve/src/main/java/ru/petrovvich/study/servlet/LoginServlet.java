package ru.petrovvich.study.servlet;


import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends AbstractUserServlet {

    public LoginServlet(TemplateProcessor templateProcessor, UserService userService) {
        super(templateProcessor, userService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);
        resp.getWriter().println(templateProcessor.getPage("login.html", parameters));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("login");
        String password = req.getParameter("password");

        UserDataSet existUser = userService.findByName(name);

        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);
        resp.setStatus(200);
        if (existUser == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
        } else if (!existUser.getPassword().equals(password)) {
            //??? TO DO
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("login", name);
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
