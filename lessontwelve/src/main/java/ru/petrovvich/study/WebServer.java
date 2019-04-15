package ru.petrovvich.study;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
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


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new RegisterServlet()), "/register");
        context.addServlet(new ServletHolder(new UserHomeServlet()), "/home");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

}
