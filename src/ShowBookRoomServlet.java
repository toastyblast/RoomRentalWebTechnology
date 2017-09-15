import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet that is triggered when the user want to see the rooms that he/she has booked. It displays a list with all of
 * the rooms that have been booked by the user.
 */
@WebServlet("/ShowBookRoomServlet")
public class ShowBookRoomServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    /**
     * Method that is used to go over all the rooms and check which rooms belong to the current user. The rooms that
     * match the user are displayed to him/her.
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);

        //Check if there is a session
        if (httpSession != null) {
            User user = (User) httpSession.getAttribute("user");
            //Check if the user is a tenant
            if (user.getOccupation().equals("tenant")) {
                boolean hasRooms = false;
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                //Print out the response page.
                out.println("<head>");
                out.println("<title>Bookings overview</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Room Rental Web Application</h1>");
                out.println("<form action=\"./ShowPersonsServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"User overview\"></form>");
                out.println("<form action=\"./LogInServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"Log out\"></form><br>");
                out.println("<ul>");
                //Loop through all of the rooms.
                for (int i = 0; i < model.getAddedRooms().size(); i++) {
                    //If the renter's username matches that of the current user's name, print the room.
                    if (model.getAddedRooms().get(i).getRenter().equals(user.getName())) {
                        out.println("<li>" + model.getAddedRooms().get(i) + "</li>");
                        hasRooms = true;
                    }
                }
                out.println("</ul>");
                //If no rooms are printed show the user that currently they have no rooms.
                if (!hasRooms) {
                    out.println("You haven't booked any rooms as of yet!<br>");
                }
                out.println("<br>To return to the search page, click the back button of your browser!");
                out.println("</body>");
            } else {
                response.sendRedirect("./NO.html");
            }
        } else {
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
