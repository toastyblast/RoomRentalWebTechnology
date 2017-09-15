import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This is a Java Servlet that is used to take the client's searching queries and compare them to the list of rooms, displaying the result.
 */
@WebServlet("/SearchRoomServlet")
public class SearchRoomServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    /**
     * This is the piece of the servlet that goes through the given queries and returns a page with a list of results.
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the user's session, if they have one.
        HttpSession session = request.getSession(false);
        //Check if this user has a session in the first place, because they aren't allowed to come here unless logged in.
        if (session != null) {
            //Get the user object that should be bound to this session.
            User user = (User) session.getAttribute("user");

            if (user.getOccupation().equals("tenant")) {
                //Just let the user continue, do nothing else that's special.
            } else {
                //They're a landlord and they should not be able to view this page.
                response.sendRedirect("./NO.html");
            }
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            response.sendRedirect("./NO.html");
        }

        int minSquareMeters = 1;
        double maxRentalFee = 9999999.99;
        String location = "";

        if (request.getParameter("squareMeters") != null) {
            try {
                minSquareMeters = Integer.parseInt(request.getParameter("squareMeters"));
            } catch (NumberFormatException nfe) {
                response.sendRedirect("./NO.html");
            }
        }
        else if (request.getParameter("rentalPrice") != null) {
            try {
                maxRentalFee = Double.parseDouble(request.getParameter("rentalPrice"));
            } catch (NumberFormatException nfe) {
                response.sendRedirect("./NO.html");
            }
        }
        if (request.getParameter("city") != null && !request.getParameter("city").equals("")) {
            location = request.getParameter("city");
        }

        ArrayList<Room> allRooms = model.getAddedRooms();
        PrintWriter out = response.getWriter();
        int roomsFound = 0;

        response.setContentType("text/html");
        out.println("<form action=\"./BookRoomServlet\" method=\"get\">");
        boolean hasRooms = false;
        for (Room currentRoom : allRooms) {
            //Take all the rooms and compare them one by one to the search queries.
            if (minSquareMeters <= currentRoom.getSquareMeters() && maxRentalFee >= currentRoom.getRentalFee()) {
                //First check if the room is as big or bigger than the given space, and if the fee is equal or less than the given fee.
                if (location.isEmpty()) {
                    out.println("<input type=\"radio\" name=\"roomForRent\" value=\"" + currentRoom.getId() + "\" checked>" + currentRoom + " <br>");
                    roomsFound++;
                    hasRooms = true;
                } else {
                    //This means the user did fill in an location, so check if this room applies to that to.
                    if (currentRoom.getLocation().equals(location)) {
                        out.println("<input type=\"radio\" name=\"roomForRent\" value=\"" + currentRoom.getId() + "\" checked>" + currentRoom + " <br>");
                        roomsFound++;
                        hasRooms = true;
                    }
                }
            }
        }
        if (hasRooms) {
            out.println("<input type=\"submit\" value=\"Book selected room\">");
            out.println("</form>");
        }
        if (roomsFound == 0) {
            //If no rooms have been found, let the user know!
            out.println("<p>No rooms matching your search queries could be found!</p>");
            out.println("<p>To try again, go back one page using the browser's back arrow!</p>");
        } else {
            out.println("<p>To return to the search menu, go back one page using the browser's back arrow!</p>");
        }
    }

    /**
     * A method that's not useful for this servlet, but altered anyways to prevent nosey users from accessing things they shouldn't.
     *
     * @param req  is the request from the user's client.
     * @param resp is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Since the doPost is not used just redirect people to the NO.html page.
        resp.sendRedirect("./NO.html");
    }
}
