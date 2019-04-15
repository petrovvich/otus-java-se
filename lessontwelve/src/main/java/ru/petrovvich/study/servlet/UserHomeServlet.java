package ru.petrovvich.study.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.petrovvich.study.model.AddressDataSet;
import ru.petrovvich.study.model.UserDataSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserHomeServlet extends AbstractUserServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserHomeServlet.class);

    public UserHomeServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);

        Map<String, Object> parameters = new HashMap<>();
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            resp.sendRedirect("/login");
            return;
        }

        String login = (String) session.getAttribute("login");
        String error = (String) session.getAttribute("error");
        String finded_name = (String) session.getAttribute("finded_name");
        String cnt_users = (String) session.getAttribute("cnt_users");

        parameters.put("error", error == null ? "" : error);
        parameters.put("finded_name", finded_name == null ? "" : finded_name);
        parameters.put("cnt_users", cnt_users == null ? "" : cnt_users);

       UserDataSet userDataSet = userService.findByName(login);
        if (userDataSet == null) {
            resp.setStatus(401);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);

        HttpSession session = req.getSession(false);

        String find = req.getParameter("find");

        if (find != null && req.getParameter("name_to_find") != null) {
            resp.setStatus(200);
            resp.sendRedirect("/home");
            UserDataSet user = userService.findById(Long.valueOf(req.getParameter("name_to_find")));
            if (user != null) {
                session.setAttribute("finded_name", "По данному id найден пользователь: " + user.getName());
                return;
            }
            session.setAttribute("finded_name", "По данному id не найдено пользователей, уточните критерии поиска.");

            return;
        }

        String count = req.getParameter("count");

        if (count != null) {
            resp.setStatus(200);
            resp.sendRedirect("/home");
            session.setAttribute("cnt_users", "Количество пользователей в базе: " + userService.getCount());
            return;
        }

        String login = req.getParameter("name");

        if (userService.exist(login)) {
            logger.warn("User is exist, return error. User login is {}", login);
            session.setAttribute("error", "Пользователь с таким логином уже существует в системе");
            resp.sendRedirect("/home");
            resp.setStatus(200);
            return;
        }

        boolean saved = userService.saveNew(req.getParameter("name"), req.getParameter("password")
                , req.getParameter("phone"), req.getParameter("city"), req.getParameter("street")
                , req.getParameter("house"));
        if (saved) {
            resp.setStatus(200);
            resp.sendRedirect("/home");
            session.setAttribute("error", "");
        }
    }
}
