import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/ShowPersonsServlet")
public class ShowPersonsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        //Anything else than the super...
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);

        //Anything else than the super...
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();
        int counter = 0;

        response.setContentType("text/html");
        //Display a list of all the users in the system.
        Object myContextParam = request.getSession().getServletContext().getAttribute("userList");
        //TODO: Don't make the list display the passwords of the users.
        response.getWriter().println(myContextParam);

        for (Cookie currentCookie : cookies) {
            //Check if the user has a counter cookie already, meaning they've been to this page before in the last 24 hours.
            if (currentCookie.getName().equals("hit_count")) {
                //If they do have a counter cookie, increase its count by 1
                counter = Integer.parseInt(currentCookie.getValue());
                counter++;
                currentCookie.setValue(counter + "");
                break;
            }
        }

        if (counter == 0) {
            //This means that the user hasn't been on this page before, so we need to make a new cookie for them.
            Cookie cookie = new Cookie("hit_count", "1");
            cookie.setMaxAge((60 * 60) * 24); //Set the cookie's max age to 24 hours.
            response.addCookie(cookie);
            counter++;
        }

        out.println("<p>This web client has visited this page  " + counter + " time(s) during the last 24 hours!</p>");
    }

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
