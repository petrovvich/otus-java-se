package ru.petrovvich.study.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.petrovvich.study.model.AddressDataSet;
import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class UserHomeServlet extends AbstractUserServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserHomeServlet.class);

    public UserHomeServlet(TemplateProcessor templateProcessor, UserService userService) {
        super(templateProcessor, userService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);

        Map<String, Object> parameters = new HashMap<>();
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            resp.sendRedirect(LOGIN_PAGE);
            return;
        }

        String error = (String) req.getAttribute(ERROR);
        parameters.put(ERROR, error == null ? "" : error);
        String cnt_users = (String) req.getAttribute(CNT_USERS);
        String finded_name = (String) req.getAttribute(FINDED_NAME);
        parameters.put(FINDED_NAME, finded_name == null ? "" : finded_name);
        parameters.put(CNT_USERS, cnt_users == null ? "" : cnt_users);

        if (hasQueryParams(req)) {
            if (getQueryParams(req).containsKey("name_to_find")) {
                findById(req, resp, parameters);
                return;
            } else if (getQueryParams(req).containsKey("count")) {
                getCountUsers(resp, parameters);
                return;
            }
        }
        findUserAndRedirect(req, resp, parameters);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(TEXT_HTML_CHARSET_UTF_8);

        HttpSession session = req.getSession(false);

        String find = req.getParameter(FIND);

        if (find != null && req.getParameter("name_to_find") != null) {

            return;
        }

        String login = req.getParameter(NAME);

        if (userService.exist(login)) {
            getErrorWhenUserExist(resp, session, login);
            return;
        }

        saveNew(req, resp, session);
    }

    private boolean hasQueryParams(HttpServletRequest req) {
        return getQueryParams(req).size() != 0;
    }

    private Map<String, String> getQueryParams(HttpServletRequest req) {
        String query = req.getQueryString();

        if (query == null) {
            return Collections.emptyMap();
        }

        List<String> params = Arrays.asList(query.split("&"));

        if (params.size() == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> stringStringMap = new HashMap<>();
        params.forEach(p -> stringStringMap.put(p.split("=")[0], p.split("=")[1]));

        return stringStringMap;
    }

    private void findById(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> parameters) throws IOException {
        resp.setStatus(200);
        UserDataSet user = userService.findById(Long.valueOf(req.getParameter("name_to_find")));
        if (user != null) {
            parameters.put(FINDED_NAME, "По данному id найден пользователь: " + user.getName());
            resp.getWriter().println(templateProcessor.getPage("home.html", parameters));
            return;
        }
        parameters.put(FINDED_NAME, "По данному id не найдено пользователей, уточните критерии поиска.");
        resp.getWriter().println(templateProcessor.getPage("home.html", parameters));
    }

    private void saveNew(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        boolean saved = userService.saveNew(req.getParameter(NAME), req.getParameter("password")
                , req.getParameter("phone"), req.getParameter("city"), req.getParameter("street")
                , req.getParameter("house"));
        if (saved) {
            resp.setStatus(200);
            resp.sendRedirect(HOME_PAGE);
            session.setAttribute(ERROR, "");
        }
    }

    private void getErrorWhenUserExist(HttpServletResponse resp, HttpSession session, String login) throws IOException {
        logger.warn("User is exist, return error. User login is {}", login);
        session.setAttribute(ERROR, "Пользователь с таким логином уже существует в системе");
        resp.sendRedirect(HOME_PAGE);
        resp.setStatus(200);
    }

    private void getCountUsers(HttpServletResponse resp, Map<String, Object> parameters) throws IOException {
        resp.setStatus(200);
        parameters.put(CNT_USERS, "Количество пользователей в базе: " + userService.getCountUsers());
        resp.getWriter().println(templateProcessor.getPage("home.html", parameters));
    }

    private void findUserAndRedirect(HttpServletRequest request, HttpServletResponse response, Map<String, Object> parameters) throws IOException {
        HttpSession session = request.getSession(false);
        String login = (String) session.getAttribute(LOGIN);
        UserDataSet userDataSet = userService.findByName(login);
        if (userDataSet == null) {
            response.setStatus(401);
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        AddressDataSet address = userDataSet.getAddress();
        if (address == null) {
            address = new AddressDataSet("не", " ", "указано");
        }
        String addressString = address.getCity() + " " + address.getStreet() + " " + address.getHouseNumber();

        parameters.put(LOGIN, userDataSet.getName());
        parameters.put("phone", userDataSet.getPhone() == null ? "не указано" : userDataSet.getPhone().getNumber());
        parameters.put("address", addressString);
        response.getWriter().println(templateProcessor.getPage("home.html", parameters));
        response.setStatus(200);
    }
}
