package main;

import accountServer.AccountServer;
import accountServer.AccountServerController;
import accountServer.AccountServerControllerMBean;
import accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resource.ResourceServerI;
import resource.ResourceServerController;
import resource.ResourceServerControllerMBean;
import resource.Resource;
import servlets.AdminPageServlet;
import servlets.HomePageServlet;
import servlets.ResourcePageServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;



public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        Thread.sleep(1000);

        logger.info("Starting at http://127.0.0.1:" + "8080");

        AccountServerI accountServer = new AccountServer(1);

        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ServerManager:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new HomePageServlet(accountServer)), HomePageServlet.PAGE_URL);

        // JMX для admin
        AccountServerI accountServerAdmin = new AccountServer(10);
        AccountServerControllerMBean serverStatisticsAdmin = new AccountServerController(accountServerAdmin);
        ObjectName nameAdmin = new ObjectName("Admin:type=AccountServerController");
        mbs.registerMBean(serverStatisticsAdmin, nameAdmin);

        // Регистрация сервлета admin
        context.addServlet(new ServletHolder(new AdminPageServlet(accountServerAdmin)), AdminPageServlet.PAGE_URL);

        // JMX для resources
        ResourceServerI iResourceServer = new Resource();
        ResourceServerControllerMBean resourceServerControllerMBean = new ResourceServerController(iResourceServer);
        ObjectName objectName = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(resourceServerControllerMBean, objectName);

        // Регистрация сервлета resource
        context.addServlet(new ServletHolder(new ResourcePageServlet(iResourceServer)), ResourcePageServlet.PAGE_URL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        // запускаем сервер
        server.start();
        java.util.logging.Logger.getGlobal().log(Level.INFO, "Server started");
        logger.info("Server started");
        server.join();
    }
}