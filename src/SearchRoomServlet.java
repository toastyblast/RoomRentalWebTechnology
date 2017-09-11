import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/SearchRoomServlet")
public class SearchRoomServlet extends HttpServlet {
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
        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

//        HttpSession userSession = request.getSession();
        int minSquareMeters = 1;
        double maxRentalFee = 9999999.99;
        String location = "";

        if (request.getParameter("squareMeters") != null) {
            minSquareMeters = Integer.parseInt(request.getParameter("squareMeters"));
        }
        if (request.getParameter("rentalPrice") != null) {
            maxRentalFee = Double.parseDouble(request.getParameter("rentalPrice"));
        }
        if (request.getParameter("city") != null && !request.getParameter("city").equals("")) {
            location = request.getParameter("city");
        }

        ArrayList<Room> allRooms = (ArrayList<Room>) request.getSession().getServletContext().getAttribute("allRooms");
        PrintWriter out = response.getWriter();
        int roomsFound = 0;
        response.setContentType("text/html");

        for (Room currentRoom : allRooms) {
            //Take all the rooms and compare them one by one to the search queries.
            if (minSquareMeters <= currentRoom.getSquareMeters() && maxRentalFee >= currentRoom.getRentalFee()) {
                //First check if the room is as big or bigger than the given space, and if the fee is equal or less than the given fee.
                if (location.isEmpty()) {
                    out.println("<p>" + currentRoom + "</p>");
                    roomsFound++;
                } else {
                    //This means the user did fill in an location, so check if this room applies to that to.
                    if (currentRoom.getLocation().equals(location)) {
                        out.println("<p>" + currentRoom + "</p>");
                        roomsFound++;
                    }
                }
            }
        }
        if (roomsFound == 0) {
            //If no rooms have been found, let the user know!
            out.println("<p>No rooms matching your search queries could be found!</p>");
            out.println("<p>To try again, go back one page using the browser's back arrow!</p>");
        } else {
            out.println("<p>To return to the search menu, go back one page using the browser's back arrow!</p>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
