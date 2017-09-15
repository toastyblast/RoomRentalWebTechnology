import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO: ...
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
     * TODO: ... ALSO ADD COMMENTS IN THIS METHOD (Caps to get your attention, I'm not mad xD)
     *
     * @param request  is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);

        if (httpSession != null) {
            User user = (User) httpSession.getAttribute("user");

            if (user.getOccupation().equals("tenant")) {
                PrintWriter out = response.getWriter();
                boolean hasRooms = false;

                for (int i = 0; i < model.getAddedRooms().size(); i++) {

                    if (model.getAddedRooms().get(i).getRenter().equals(user.getName())) {
                        out.println("<h1>" + model.getAddedRooms().get(i) + "<h1>");
                        hasRooms = true;
                    }
                }

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
