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

    /**
     * Method for displaying the add room form and also any rooms that belong to this landlord.
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
            User currentUser = (User) session.getAttribute("user");

            if (currentUser.getOccupation().equals("landlord")) {
                //Since we now know it's an actual landlord logged in, we can show whatever the landlord should see on this page.
                //Get his list of rooms!
                ArrayList arrayList = model.getRooms(currentUser);
                //Since the contents of this page is dynamic and not static, the HTML has to be made here in println()'s.
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<head>");
                out.println("<title>Room overview</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Room Rental Web Application</h1>");
                out.println("<form action=\"./ShowPersonsServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"User overview\"></form>");
                out.println("<form action=\"./LogInServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"Log out\"></form>");
                out.println("<br>");
                out.println("<form action=\"./OverviewServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"Add a room\"></form>");
                if (arrayList == null) {
                    //If the landlord has no rooms yet, tell them!
                    out.println("You currently have no rooms added yet!");
                } else {
                    out.println("<ul>");
                    for (Object anArrayListItem : arrayList) {
                        out.println("<li>" + anArrayListItem + "</li>");
                    }
                    out.println("</ul>");
                }
                out.println("</body>");
            } else {
                //They're a landlord and they should not be able to view this page.
                response.sendRedirect("./NO.html");
            }
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            response.sendRedirect("./NO.html");
        }
    }

    /**
     * Method for when a landlord has provided information for a room and clicked the "Add room" button.
     *
     * @param req  is the request from the user's client.
     * @param resp is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get the user's session, if they have one.
        HttpSession session = req.getSession(false);
        //Check if this user has a session in the first place, because they aren't allowed to come here unless logged in.
        if (session != null) {
            //Get the user object that should be bound to this session.
            User currentUser = (User) session.getAttribute("user");

            if (currentUser.getOccupation().equals("landlord")) {
                //If there is an array list "connected" to the user, retrieve it from the ServletContext add to it the new room
                //and then update the list in the ServletContext.

                boolean check = true;
                //Check if the information is OK.
                if (req.getParameter("city").isEmpty() || req.getParameter("city") == null) {
                    resp.sendRedirect("./NO.html");
                    check = false;
                }
                if (check){
                    String location = req.getParameter("city");
                    int squareMeters = 0;
                    double rentalPrice = 0.00;
                    //Intercept any attempts at providing invalid data that might crash the web application.
                    try {
                        squareMeters = Integer.parseInt(req.getParameter("squareMeters"));
                    } catch (NumberFormatException nfe) {
                        resp.sendRedirect("./NO.html");
                    }
                    try {
                        rentalPrice = Double.parseDouble(req.getParameter("rentalPrice"));
                    } catch (NumberFormatException nfe) {
                        resp.sendRedirect("/.NO.html");
                    }


                    int before = model.getAddedRooms().size();
                    model.addRoom(location, squareMeters, rentalPrice, currentUser.getName());
                    int after = model.getAddedRooms().size();

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("text/html");
                    if (before < after) {
                        out.println("Your room has been successfully added:");
                        out.println("<br>" + model.getAddedRooms().get(model.getAddedRooms().size() - 1) + "<br><br>");
                    }

                    out.println("<a href=\"./ShowRoomsServlet\">Click here</a> to return to the overview of your rooms.");
                }

            } else {
                //They're a landlord and they should not be able to view this page.
                resp.sendRedirect("./NO.html");
            }
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            resp.sendRedirect("./NO.html");
        }
    }
}
