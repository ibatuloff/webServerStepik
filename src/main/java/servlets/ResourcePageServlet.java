package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.SaxXMLReader;
import resource.Resource;
import resource.ResourceServerI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcePageServlet extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(ResourcePageServlet.class.getName());
    public static final String PAGE_URL = "/resource";
    private final ResourceServerI iResourceServer;

    public ResourcePageServlet(ResourceServerI iResourceServer) {
        this.iResourceServer = iResourceServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");

        String path = req.getParameter("path");

        if (path != null && !path.isEmpty()) {
            Resource testResource = (Resource) SaxXMLReader.readXML(path);
            if(testResource != null) {
                iResourceServer.setAge(testResource.getAge());
                iResourceServer.setName(testResource.getName());

                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }

        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
}