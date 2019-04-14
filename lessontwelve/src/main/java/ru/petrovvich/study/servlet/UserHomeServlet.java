package ru.petrovvich.study.servlet;

import ru.petrovvich.study.model.AddressDataSet;
import ru.petrovvich.study.model.PhoneDataSet;
import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

public class UserHomeServlet extends HttpServlet {

    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

    private final TemplateProcessor templateProcessor;
    private final UserService userService;


    public UserHomeServlet() {
        this.templateProcessor = new TemplateProcessor();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        String login = (String) req.getSession(false).getAttribute("login");
        UserDataSet userDataSet = userService.findByName(login);
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);
        if (userDataSet ==null) {
            resp.setStatus(401);
            resp.sendRedirect(req.getContextPath() + "/login");
        }

        AddressDataSet address = userDataSet.getAddress();

        if (address == null) {
            address = new AddressDataSet("не", " ", "указано");
        }

        String addressString = address.getCity() + " " + address.getStreet() + " " + address.getHouseNumber();

        parameters.put("login", userDataSet.getName());
        parameters.put("phone", userDataSet.getPhone() == null ? "не указано" : userDataSet.getPhone().getNumber());
        parameters.put("address", addressString);
        resp.getWriter().println(templateProcessor.getPage("home.html", parameters));
        resp.setStatus(200);
    }
}
