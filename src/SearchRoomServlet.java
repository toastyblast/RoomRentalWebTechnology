import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        HttpSession userSession = request.getSession();
        int minSquareMeters = 1;
        double maxRentalFee = 9999999.99;
        String location = "";

        if (request.getParameter("squareMeters") != null) {
            minSquareMeters = Integer.parseInt(request.getParameter("squareMeters"));
        }
        if (request.getParameter("rentalPrice") != null) {
            maxRentalFee = Integer.parseInt(request.getParameter("rentalPrice"));
        }
        if (request.getParameter("city") != null && !request.getParameter("city").equals("")) {
            location = request.getParameter("city");
        }

        ArrayList<Room> allRooms = (ArrayList<Room>) request.getSession().getServletContext().getAttribute("allRooms");
        for (int i = 0 ; i < allRooms.size() ; i++){
            Room currentRoom = allRooms.get(i);
            if (minSquareMeters <= currentRoom.getSquareMeters() && maxRentalFee >= currentRoom.getRentalFee()){
                if (location.isEmpty()){
                    response.setContentType("text/html");
                    response.getWriter().println("<p>" + currentRoom + "</p>");
                }else if (!location.isEmpty()){
                    if (currentRoom.getLocation().equals(location)){
                        response.setContentType("text/html");
                        response.getWriter().println("<p>" + currentRoom + "</p>");
                    }
                }

            }
        }
//        response.getWriter().println(allRooms);

        //TODO: Matching searching queries here and then display the list of rooms.
    }

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
