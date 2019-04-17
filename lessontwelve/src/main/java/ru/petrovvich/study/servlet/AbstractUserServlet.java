package ru.petrovvich.study.servlet;

import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.service.UserService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractUserServlet extends HttpServlet {

    static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

    final TemplateProcessor templateProcessor;
    final UserService userService;

    AbstractUserServlet(TemplateProcessor templateProcessor, UserService userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }
}
