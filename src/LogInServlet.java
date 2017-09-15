import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet is used to log in existing users and send them to their right page or log them out.
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    /**
     * This doPost is called whenever a user logs in from login.html. It sends the user to the page appropriate for their credentials.
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the information of the user from the log in form.

        //Check if the information that is received from the form is OK.
        if (request.getParameter("username").isEmpty() || request.getParameter("username") == null) {
            response.sendRedirect("./NO.html");
        }
        if (request.getParameter("password").isEmpty() || request.getParameter("password") == null) {
            response.sendRedirect("./NO.html");
        }

        User user = new User(request.getParameter("username"), request.getParameter("password"), " ");
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
            //The user did not supply correct credentials, let them know!
            response.sendRedirect("./invalidcredentials.html");
        } else if (request.getSession(false) != null) {
            PrintWriter out = response.getWriter();
            //There is still an active account on this browser, let the user either log out or return to the login menu
            out.println("<h1>Oops, login conflict! :(</h1>");
            out.println("<p>It seems that you're already logged in this browser in the past hour. This means we can't log you in here!</p>");
            out.println("To go back to the login screen, <a href=\"./login.html\">click here</a><br><br>");
            out.println("Optionally, you could also force log the other account out by pressing the button and try to login again!");
            out.println("To force such a logout across all active tabs, <a href=\"./LogInServlet\">click here</a>");
        } else if (correctInfo) {
            //Set the information for the "currently logged" user.
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.getOccupation().equals("tenant")) {
                //If the user is a tenant, send them to the tenant.html page
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/tenant.html");
                dispatcher.forward(request, response);
            } else if (user.getOccupation().equals("landlord")) {
                //If the user is a landlord, send them to their rooms overview servlet!
                response.sendRedirect("./ShowRoomsServlet");
            }
        }
    }

    /**
     * This part of the LogInServlet is called when a user presses the "Log out" button on any page. It indeed logs out the user's session.
     *
     * @param req  is the request from the user's client.
     * @param resp is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get the user's session, if they have one.
        HttpSession httpSession = req.getSession(false);
        //Check if this user has a session in the first place.
        if (httpSession != null) {
            //If they do, invalidate their session and redirect them back to the login screen.
            httpSession.invalidate();
            resp.sendRedirect("./login.html");
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            resp.sendRedirect("./NO.html");
        }
    }
}
