package main;

import AccountService.AccountService;
import database.DatabaseService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            DatabaseService dbService = new DatabaseService();
            AccountService accountService = new AccountService(dbService);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
            context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("public_html");

            HandlerList handlers = new HandlerList();
//            final Handler[] handler = {resource_handler, context};
            handlers.setHandlers(new Handler[] {resourceHandler, context});

            Server server = new Server(8080);
            server.setHandler(handlers);

            server.start();
            System.out.println("Server started");
            server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}