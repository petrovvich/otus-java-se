package ru.petrovich.study.frontend.servlet;


import lombok.extern.slf4j.Slf4j;
import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.frontend.processor.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginServlet extends AbstractUserServlet {

    public LoginServlet(TemplateProcessor templateProcessor, FrontendService frontendService) {
        super(templateProcessor, frontendService);
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
        String name = req.getParameter(LOGIN);
        String password = req.getParameter("password");

        performRequest(name);

        UserDataSet existUser = frontendService.getUsers().get(name);

        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);
        resp.setStatus(200);
        if (existUser == null) {
            resp.sendRedirect(req.getContextPath() + REGISTER_PAGE);
        } else if (!existUser.getPassword().equals(password)) {
            log.error("Not inplemented yet!");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute(LOGIN, name);
            resp.sendRedirect(req.getContextPath() + HOME_PAGE);
        }
    }
}
