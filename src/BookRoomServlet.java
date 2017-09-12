import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/BookRoomServlet")
public class BookRoomServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();
        model = (Model) getServletContext().getAttribute("model");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null){
            String roomId = request.getParameter("roomForRent");
            if (roomId != null && !roomId.isEmpty()) {
                if (!model.checkRoomAvailable(Integer.parseInt(roomId))) {
                    response.getWriter().println("Room is already booked.");
                } else {
                    User user = (User) request.getSession().getAttribute("user");
                    model.bookRoom(Integer.parseInt(roomId), user);
                }
            }
        }else {
            response.sendRedirect("NO.html");
        }

    }
}
