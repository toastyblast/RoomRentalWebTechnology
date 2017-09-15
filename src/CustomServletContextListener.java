import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * TODO: ...
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
