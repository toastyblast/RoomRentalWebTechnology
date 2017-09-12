import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Java Servlet that is used to show a page of all users in the system and a hit counter for the person page.
 */
@WebServlet("/ShowPersonsServlet")
public class ShowPersonsServlet extends HttpServlet {
    private int counter;
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();
        model = (Model) getServletContext().getAttribute("model");
        counter = 0;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);

        //Anything else than the super...
    }


    /**
     * The piece of code that shows the lsit of all users on the website and the amount of times it has been visited by
     * the client's browser in a set amount of time.
     *
     * @param request is the request from the user's client.
     * @param response is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();

        response.setContentType("text/html");
        //Display a list of all the users in the system.
        //TODO: Don't make the list display the passwords of the users.
        if (cookies != null) {
            //Check if the array of cookies isn't empty.
            for (Cookie currentCookie : cookies) {
                //Check if the user's web client has a counter cookie already, meaning they've been to this page before in the last 24 hours.
                if (currentCookie.getName().equals("hit_count")) {
                    //If they do have a counter cookie, increase its count by 1
                    counter = Integer.parseInt(currentCookie.getValue());
                    counter++;
                    currentCookie.setValue(counter + "");
                    currentCookie.setMaxAge((60 * 60) * 24); //Set the cookie's max age to 24 hours, as you'll redeploy it.
                    response.addCookie(currentCookie);
                }
            }
        }
        if (counter == 0) {
            //This means that the user's web client hasn't been on this page before, so we need to make a new cookie for them.
            Cookie cookie = new Cookie("hit_count", "1");
            cookie.setMaxAge((60 * 60) * 24); //Set the cookie's max age to 24 hours.
            response.addCookie(cookie);
            counter++;
        }

        out.println("<p>Your current web client has visited this page " + counter + " time(s) during the last 24 hours</p>");
        counter = 0; //Reset the counter again so that whenever the max age has been passed, since then the production
        // of a new counter cookie for the next 24 hours.

        ArrayList<User> users = model.getRegisteredUsers();
        out.println("<ul>");
        for (Object user : users){
            out.println("<li>" + user + "</li>");
        }
        out.println("</ul>");
    }

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
