import javax.servlet.RequestDispatcher;
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

/**
 * This Servlet is used to display a list of the currently logged in landlord's rooms and allow him to add a room as well.
 */
@WebServlet("/ShowRoomsServlet")
public class ShowRoomsServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();
        model = (Model) getServletContext().getAttribute("model");

    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);

        //Anything else than the super...
    }

    /**
     * Method for displaying the add room form and also any rooms that belong to this landlord.
     *
     * @param request is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the current user's info(landlord).
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String userPassword = (String) session.getAttribute("userPassword");
        String userType = (String) session.getAttribute("userType");
        User currentUser = new User(userName, userPassword, userType);

        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

        //Since we do not know how to only reload a certain part of the page, we instead reload the whole page here.
        // Sorry for this, but as mentioned, we have no idea how to only reload certain parts of HTML.
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Room registration</title>");
//        out.println("</head>");
//        out.println("<body bgcolor=\"white\">");
//        out.println("<h1>Room Rental Web Application</h1>");
//        out.println("<form action=\"/ShowPersonsServlet\" method=\"get\">");
//        out.println("<input type=\"submit\" value=\"User overview\">");
//        out.println("</form>");
//        out.println("<form action=\"./\" method=\"get\">");
//        out.println("<input type=\"submit\" value=\"Log out\">");
//        out.println("</form>");
//        out.println("<br>");
//        out.println("<form action=\"/ShowRoomsServlet\" method=\"post\">");
//        out.println("Room surface in square meters:");
//        out.println("<input type=\"number\" name=\"squareMeters\" min=\"1\" max=\"9999999\" value=\"1\" required>");
//        out.println("<br>");
//        out.println("Rental fee in Euro's/month:");
//        out.println("<input type=\"number\" name=\"rentalPrice\" min=\"1.00\" max=\"9999999.99\" value=\"1.00\" step=\".01\" required>");
//        out.println("<br>");
//        out.println("City where the room is located:");
//        out.println("<input type=\"text\" name=\"city\" placeholder=\"I.E. Enschede\" required>");
//        out.println("<br><br>");
//        out.println("<input type=\"submit\" value=\"Add room\">");
//        out.println("</form>");
//        //Retrieve the array list which contains the rooms that the landlord owns and display them, if there are any.
//        Object roomList = request.getSession().getServletContext().getAttribute(currentUser.getName());
//        if (roomList == null) {
//            out.println("<h14> no rooms added yet </h14>");
//        } else {
//            out.println("<h14> " + roomList + "  </h14>");
//        }
//        out.println("</body>");
//        out.println("</html>");


        ArrayList arrayList = model.getRooms(currentUser);

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println(" To add room, <a href=\"./???.html\">click here.</a>");
        out.println("<ul>");
        for (Object anArrayList : arrayList) {
            out.println("<li>" + anArrayList + "</li>");
        }
        out.println("</ul>");
        out.println("<body>");
        out.println("</html>");
    }

    /**
     * Method for when a landlord has provided information for a room and clicked the "Add room" button.
     *
     * @param req is the request from the user's client.
     * @param resp is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get the information of the currently logged user.
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("userName");
        String userPassword = (String) session.getAttribute("userPassword");
        String userType = (String) session.getAttribute("userType");
        User currentUser = new User(userName, userPassword, userType);

        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

        //If there is an array list "connected" to the user, retrieve it from the ServletContext add to it the new room
        //and then update the list in the ServletContext.

        String location = req.getParameter("city");
        int squareMeters = Integer.parseInt(req.getParameter("squareMeters"));
        double rentalPrice = Double.parseDouble(req.getParameter("rentalPrice"));
        Room room = new Room(location, squareMeters, rentalPrice, currentUser.getName());

        model.getAddedRooms().add(room);

        resp.getWriter().println(model.getAddedRooms());
    }

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
