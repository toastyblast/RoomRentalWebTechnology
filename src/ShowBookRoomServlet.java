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
                PrintWriter out = response.getWriter();
                boolean hasRooms = false;

                //Loop through all of the rooms.
                for (int i = 0; i < model.getAddedRooms().size(); i++) {

                    //If the renter's username matches that of the current user's name, print the room.
                    if (model.getAddedRooms().get(i).getRenter().equals(user.getName())) {
                        out.println("<h1>" + model.getAddedRooms().get(i) + "<h1>");
                        hasRooms = true;
                    }
                }

                //If no rooms are printed show the user that currently they have no rooms.
                if (!hasRooms) {
                    response.getWriter().println("You have no booked rooms.");
                }
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
