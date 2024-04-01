package main;

import dbService.DBService;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) throws Exception {
        // Подключение к БД
        DBService dbService = new DBService();
        dbService.printConnectInfo();
        // Контекст
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // Сервлеты по обработке запросов
        context.addServlet(new ServletHolder(new SignUpServlet(dbService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(dbService)), "/signin");
        // Обработчик ресурсов
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        // Регистрация обработчиклв
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        // Запуск
        Server server = new Server(8080);
        server.setHandler(handlers);
        server.start();
        Logger.getGlobal().log(Level.INFO, "Server started");
        server.join();
    }
}