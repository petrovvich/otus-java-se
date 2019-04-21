package ru.petrovvich.study.servlet;

import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractUserServlet extends HttpServlet {

    static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

    static final String ERROR = "error";
    static final String FINDED_NAME = "finded_name";
    static final String CNT_USERS = "cnt_users";
    static final String LOGIN = "login";
    static final String FIND = "find";
    static final String COUNT = "count";
    static final String NAME = "name";

    static final String LOGIN_PAGE = "/login";
    static final String HOME_PAGE = "/home";
    protected static final String REGISTER_PAGE = "/register";

    final TemplateProcessor templateProcessor;
    final UserService userService;

    AbstractUserServlet(TemplateProcessor templateProcessor, UserService userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }
}
