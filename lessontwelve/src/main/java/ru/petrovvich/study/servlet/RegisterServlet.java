package ru.petrovvich.study.servlet;

import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.model.AddressDataSet;
import ru.petrovvich.study.model.PhoneDataSet;
import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {

    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";
    private final TemplateProcessor templateProcessor;
    private final UserService userService;


    public RegisterServlet() {
        this.templateProcessor = new TemplateProcessor();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage("register.html", parameters));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);

        boolean saved = userService.saveNew(new UserDataSet(req.getParameter("login")
                , req.getParameter("password")
                , new PhoneDataSet(req.getParameter("phone"))
                , new AddressDataSet(req.getParameter("city"), req.getParameter("street"), req.getParameter("house"))));

        if (saved) {
            req.getSession().setAttribute("user", req.getParameter("login"));
            resp.sendRedirect("home");
            resp.setStatus(200);
        }
    }
}
