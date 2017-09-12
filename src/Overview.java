import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Overview")
public class Overview extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String type = (String) httpSession.getAttribute("userType");
        if (request.getSession(false) != null){
            if (type.equals("landlord")){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/./WEB-INF/addroom.html");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("NO.html");
            }
        }else {
            response.sendRedirect("NO.html");
        }

    }
}
