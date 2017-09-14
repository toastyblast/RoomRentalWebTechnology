import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ShowBookRoomServlet")
public class ShowBookRoomServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();
        model = (Model) getServletContext().getAttribute("model");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession httpSession = request.getSession(false);

        if (httpSession != null){

            User user = (User) httpSession.getAttribute("user");

            if (user.getOccupation().equals("tenant")){

                PrintWriter out = response.getWriter();
                boolean hasRooms = false;

                for (int i = 0 ; i < model.getAddedRooms().size() ; i++){
                    if (model.getAddedRooms().get(i).getRenter().equals(user.getName())){
                        out.println("<h1>" + model.getAddedRooms().get(i) + "<h1>" );
                        hasRooms = true;
                    }

                }

                if (!hasRooms){
                    response.getWriter().println("You have no booked rooms.");
                }
            } else {
                response.sendRedirect("NO.html");
            }

        } else {
            response.sendRedirect("NO.html");
        }
    }
}
