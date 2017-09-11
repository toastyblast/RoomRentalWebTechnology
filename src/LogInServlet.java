import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {

    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the information of the user from the log in form.
        User user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("userType"));
        boolean correctInfo;

        //Check if there are any registered users.
        //1.If there are none redirect them to the invalidcredentials.html.
        //2.If there are registered users check if the user name password combination from the form match any of the
        //registered users.
        //2.1 If the information is correct redirect the user to either the tenant or landlord page, depending on the
        //user type.
        //2.2 If the user information is wrong redirect to invalidcredentials.html.

        //Get the userList from the ServletContext.

        correctInfo = model.checkUser(user);

        if (!correctInfo) {
            response.sendRedirect("./invalidcredentials.html");
        } else if (correctInfo) {
            //Set the information for the "currently logged" user.
            HttpSession session = request.getSession();
            session.setAttribute("userName", user.getName());
            session.setAttribute("userPassword", user.getPass());
            session.setAttribute("userType", user.getOccupation());

            if (user.getOccupation().equals("tenant")) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/tenant.html");
                dispatcher.forward(request, response);
            } else if (user.getOccupation().equals("landlord")) {
                response.sendRedirect("./ShowRoomsServlet");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Log the user out using this and redirect them back to the login page.
    }
}
