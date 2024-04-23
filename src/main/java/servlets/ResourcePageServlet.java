package servlets;

import resource.ResourceServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResourcePageServlet extends HttpServlet {
    public static final String PAGE_URL = "/resources";
    private final ResourceServer resourceServer;

    public ResourcePageServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getParameter("path");
        response.setContentType("text/html;charset=utf-8");

        resourceServer.readResource(path);

        response.getWriter().printf("Loaded: " + path + "<br>");
        response.getWriter().printf(
                "Class name: " + resourceServer.getNameClass() + "<br>" +
                        "Name: " + resourceServer.getName() + "<br>" +
                        "Age: " + resourceServer.getAge() + "<br>"
        );
        response.setStatus(HttpServletResponse.SC_OK);
    }
}