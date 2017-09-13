import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/OverviewServlet")
public class OverviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO..? Do something here to check if someone didn't force to do a doPost through a service like POSTMAN.
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the user's session, if they have one.
        HttpSession httpSession = request.getSession(false);
        //Check if this user has a session in the first place.
        if (httpSession != null){
            User user = (User) httpSession.getAttribute("user");
            String type = user.getOccupation();
            //If they do, that means they've logged in before. Then check if the user is a landlord, and not a tenant.
            if (type.equals("landlord")){
                //If they are a landlord, forward them to the secret addroom.html page.
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addroom.html");
                dispatcher.forward(request, response);
            } else {
                //If they're a tenant, let them know they shouldn't be here.
                response.sendRedirect("./NO.html");
            }
        } else {
            //If they are not logged in at all, let them know they shouldn't be here.
            response.sendRedirect("./NO.html");
        }
    }
}
