import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {

    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: If a user is already logged in but for some reason returned to the login page and entered new credentials,
        // TODO: check for that here. Log out their old session in that case and create a new one with the new credentials
        // TODO: Another possibility is that they used a service like POSTMAN to force a POST, which we should check for too.

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
            session.setAttribute("user", user);

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
        //Get the user's session, if they have one.
        HttpSession httpSession = req.getSession(false);
        //Check if this user has a session in the first place.
        if (httpSession != null){
            //If they do, invalidate their session and redirect them back to the login screen.
            httpSession.removeAttribute("user");
            resp.sendRedirect("./login.html");
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            resp.sendRedirect("./NO.html");
        }
    }
}
