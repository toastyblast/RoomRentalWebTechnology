import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("userType"));
        boolean correctInfo = false;

        if (request.getSession().getServletContext().getAttribute("userList") == null) {
            response.sendRedirect("invalidcredentials.html");
        } else {
            Object myContextParam = request.getSession().getServletContext().getAttribute("userList");
            ArrayList<User> users = (ArrayList<User>) myContextParam;

            for (int i = 0; users.size() > i; i++) {
                User user1 = users.get(i);

                if (user.getName().equals(user1.getName()) && user.getPass().equals(user1.getPass())) {
                    correctInfo = true;
                    user = user1;
                }
            }

            if (!correctInfo) {
                response.sendRedirect("invalidcredentials.html");
            } else if (correctInfo) {

                request.getSession().getServletContext().setAttribute("currentUser", user);

                if (user.getOccupation().equals("tenant")) {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/tenant.html");
                    dispatcher.forward(request, response);
                } else if (user.getOccupation().equals("landlord")) {
                    ServletContext context= getServletContext();
                    RequestDispatcher rd= context.getRequestDispatcher("/ShowRoomsServlet");
                    rd.forward(request, response);
                }



//                response.getWriter().println("ok");
            }
        }
    }
}
