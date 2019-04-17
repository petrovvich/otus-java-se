package ru.petrovvich.study;

import freemarker.template.Configuration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.petrovvich.study.processor.TemplateProcessor;
import ru.petrovvich.study.service.DBServiceHibernateImpl;
import ru.petrovvich.study.service.UserService;
import ru.petrovvich.study.service.UserServiceImpl;
import ru.petrovvich.study.servlet.LoginServlet;
import ru.petrovvich.study.servlet.RegisterServlet;
import ru.petrovvich.study.servlet.UserHomeServlet;

public class WebServer {

    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        UserService userService = new UserServiceImpl(new DBServiceHibernateImpl());
        TemplateProcessor templateProcessor = new TemplateProcessor(new Configuration(Configuration.VERSION_2_3_28));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userService)), "/login");
        context.addServlet(new ServletHolder(new RegisterServlet(templateProcessor, userService)), "/register");
        context.addServlet(new ServletHolder(new UserHomeServlet(templateProcessor, userService)), "/home");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

}
