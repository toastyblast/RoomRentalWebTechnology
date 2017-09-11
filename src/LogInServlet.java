import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Java Servlet that checks a client's given credentials and makes sure to forward them to the right pages.
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

        //Get the information of the user from the log in form.
        User user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("userType"));
        boolean correctInfo = false;

        //Check if there are any registered users.
        //1.If there are none redirect them to the invalidcredentials.html.
        //2.If there are registered users check if the user name password combination from the form match any of the
        //registered users.
        //2.1 If the information is correct redirect the user to either the tenant or landlord page, depending on the
        //user type.
        //2.2 If the user information is wrong redirect to invalidcredentials.html.
        if (request.getSession().getServletContext().getAttribute("userList") == null) {
            response.sendRedirect("invalidcredentials.html");
        } else {
            //Get the userList from the ServletContext.
            Object myContextParam = request.getSession().getServletContext().getAttribute("userList");
            ArrayList<User> users = (ArrayList<User>) myContextParam;

            for (int i = 0; users.size() > i; i++) {
                User user1 = users.get(i);

                if (user.getName().equals(user1.getName()) && user.getPass().equals(user1.getPass())) {
                    correctInfo = true;
                    user = user1;
                    break;
                }
            }

            if (!correctInfo) {
                response.sendRedirect("invalidcredentials.html");
            } else {
                //Set the information for the "currently logged" user.
                HttpSession session = request.getSession();
                session.setAttribute("userName", user.getName());
                session.setAttribute("userPassword", user.getPass());
                session.setAttribute("userType", user.getOccupation());

                if (user.getOccupation().equals("tenant")) {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/tenant.html");
                    dispatcher.forward(request, response);
                } else if (user.getOccupation().equals("landlord")) {
                    ServletContext context = getServletContext();
                    response.sendRedirect("/ShowRoomsServlet");
                }
            }
        }
    }
}
