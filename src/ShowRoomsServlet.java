import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ShowRoomsServlet")
public class ShowRoomsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        //Anything else than the super...
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);

        //Anything else than the super...
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //...
        Object myContextParam = request.getSession().getServletContext().getAttribute("userList");
        response.getWriter().println(myContextParam);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //...
    }

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
