import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet that is responsible for sending a Landlord type user to the addroom.html page when the landlord clicks on the
 * "Add room" button on the ShowRoomsServlet.
 */
@WebServlet("/OverviewServlet")
public class OverviewServlet extends HttpServlet {
    /**
     * Method that sends the landlord type user from the ShowRoomsServlet to the addroom.html.
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the user's session, if they have one.
        HttpSession httpSession = request.getSession(false);
        //Check if this user has a session in the first place.
        if (httpSession != null) {
            User user = (User) httpSession.getAttribute("user");
            String type = user.getOccupation();
            //If they do, that means they've logged in before. Then check if the user is a landlord, and not a tenant.
            if (type.equals("landlord")) {
                //If they are a landlord, forward them to the secret addroom.html page.
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("./WEB-INF/addroom.html");
                dispatcher.forward(request, response);
            } else {
                //If they're a tenant, let them know they shouldn't be here.
                response.sendRedirect("./NO.html");
            }
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            response.sendRedirect("./NO.html");
        }
    }

    /**
     * A method that's not useful for this servlet, but altered anyways to prevent nosey users from accessing things they shouldn't.
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Since the doPost is not used just redirect people to the NO.html page.
        response.sendRedirect("./NO.html");
    }
}
