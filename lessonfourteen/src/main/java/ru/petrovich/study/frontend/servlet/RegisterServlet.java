package ru.petrovich.study.frontend.servlet;

import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.frontend.processor.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends AbstractUserServlet {

    public RegisterServlet(TemplateProcessor templateProcessor, FrontendService frontendService) {
        super(templateProcessor, frontendService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);
        resp.getWriter().println(templateProcessor.getPage("register.html", parameters));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);

        performRequest(req.getParameter(LOGIN), req.getParameter("password"),
                req.getParameter("phone"), req.getParameter("city"), req.getParameter("street"),
                req.getParameter("house"));

        req.getSession().setAttribute("user", req.getParameter(LOGIN));
        resp.sendRedirect("home");
        resp.setStatus(200);
    }
}
