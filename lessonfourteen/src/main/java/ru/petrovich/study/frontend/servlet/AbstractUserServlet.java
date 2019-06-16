package ru.petrovich.study.frontend.servlet;

import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.frontend.processor.TemplateProcessor;

import javax.servlet.http.HttpServlet;

public abstract class AbstractUserServlet extends HttpServlet {

    static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

    static final String ERROR = "error";
    static final String FINDED_NAME = "finded_name";
    static final String CNT_USERS = "cnt_users";
    static final String LOGIN = "login";
    static final String FIND = "find";
    static final String NAME = "name";

    static final String LOGIN_PAGE = "/login";
    static final String HOME_PAGE = "/home";
    static final String REGISTER_PAGE = "/register";

    final TemplateProcessor templateProcessor;
    final FrontendService frontendService;

    AbstractUserServlet(TemplateProcessor templateProcessor, FrontendService frontendService) {
        this.templateProcessor = templateProcessor;
        this.frontendService = frontendService;
    }

    void performRequest(String name) {
        synchronized (this) {
            frontendService.handleRequest(name);
            try {
                wait(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void performRequest(Long id) {
        synchronized (this) {
            frontendService.handleRequest(id);
            try {
                wait(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void performRequest(String login, String password, String phone, String city, String street, String house) {
        synchronized (this) {
            frontendService.handleRequest(login, password, phone, city, street, house);
            try {
                wait(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void performRequest() {
        synchronized (this) {
            frontendService.handleRequest();
            try {
                wait(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
