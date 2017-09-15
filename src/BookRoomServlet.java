import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class that is used to book rooms. It gets the id of the room that was passed to it, uses the id to check if the room
 * is free and if it is free, it is booked by the user.
 */
@WebServlet("/BookRoomServlet")
public class BookRoomServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    /**
     * Method that is called when you submit the form from the SearchRoomServlet. It checks if the room can be booked
     * and then gives it to the user.
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String roomId = request.getParameter("roomForRent");

            if (roomId != null && !roomId.isEmpty()) {
                //Check if the room is actually existent and then if it's available too.
                if (!model.checkRoomAvailable(Integer.parseInt(roomId))) {
                    //Tell the user the room is already booked if they tried to select such a room.
                    response.setContentType("text/html");
                    response.getWriter().println("Room is already booked. Click on the back button of your browser to return to your user page!");
                } else {
                    //Get the user from the current session.
                    User user = (User) request.getSession().getAttribute("user");
                    //Tell the system that they booked the room.
                    model.bookRoom(Integer.parseInt(roomId), user);
                    //Then return the user back to the tenant screen.
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/tenant.html");
                    dispatcher.forward(request, response);
                }
            }
        } else {
            response.sendRedirect("NO.html");
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
        response.sendRedirect("NO.html");
    }
}
