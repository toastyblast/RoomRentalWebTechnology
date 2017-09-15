import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Methods from these class are executed every time when the server start and when the server stops. So we are using
 * this class to load our model automatically every time the server is started.
 */
@WebListener
public class CustomServletContextListener implements ServletContextListener {
    /**
     * Every time the server starts, put the model into the servlet context.
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Model model = new Model();
        servletContext.setAttribute("model", model);
    }
}
