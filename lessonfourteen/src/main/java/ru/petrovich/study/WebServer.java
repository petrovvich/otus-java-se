package ru.petrovich.study;

import freemarker.template.Configuration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.petrovich.study.backend.BackendService;
import ru.petrovich.study.backend.BackendServiceImpl;
import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.frontend.FrontendServiceImpl;
import ru.petrovich.study.frontend.processor.TemplateProcessor;
import ru.petrovich.study.backend.service.DBServiceHibernateImpl;
import ru.petrovich.study.backend.service.UserService;
import ru.petrovich.study.backend.service.UserServiceImpl;
import ru.petrovich.study.frontend.servlet.LoginServlet;
import ru.petrovich.study.frontend.servlet.RegisterServlet;
import ru.petrovich.study.frontend.servlet.UserHomeServlet;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MessageSystem;
import ru.petrovich.study.messaging.MessageSystemContext;

public class WebServer {

    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);
        UserService userService = new UserServiceImpl(new DBServiceHibernateImpl());

        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("front");
        messageSystemContext.setFrontAddress(frontAddress);
        Address dbAddress = new Address("back");
        messageSystemContext.setDbAddress(dbAddress);
        FrontendService frontendService = new FrontendServiceImpl(messageSystemContext, frontAddress);
        frontendService.init();
        BackendService dbService = new BackendServiceImpl(messageSystemContext, dbAddress, userService);
        dbService.init();
        messageSystem.start();

        TemplateProcessor templateProcessor = new TemplateProcessor(new Configuration(Configuration.VERSION_2_3_28));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor, frontendService)), "/login");
        context.addServlet(new ServletHolder(new RegisterServlet(templateProcessor, frontendService)), "/register");
        context.addServlet(new ServletHolder(new UserHomeServlet(templateProcessor, frontendService)), "/home");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

}
