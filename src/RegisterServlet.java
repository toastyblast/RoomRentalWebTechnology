import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Java Servlet that is used to check requested user credentials for registration and storing them if unique.
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    /**
     * Method that is called by the registration.html file to possibly register a new user with given credentials
     *
     * @param req is the request from the user's client.
     * @param resp is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Check if the access to this page has been done by a landlord logging in, and not someone who just typed in the URL into their browser's bar.

        boolean unique = true;

        //Check if there is a user list, if not create one.
        if (req.getSession().getServletContext().getAttribute("userList") == null) {
            ArrayList<User> arrayList = new ArrayList<>();
            req.getSession().getServletContext().setAttribute("userList", arrayList);
        }

        //If there is a user list, check whether the credentials are unique, if yes add the user to the list.
        if (req.getSession().getServletContext().getAttribute("userList") != null) {
            //Retrieve the userList from the ServletContext.
            Object myContextParam = req.getSession().getServletContext().getAttribute("userList");
            ArrayList<User> arrayList = (ArrayList<User>) myContextParam;
            User user = new User(req.getParameter("chosenUsername"), req.getParameter("chosenPassword"), req.getParameter("userType"));

            for (User user1 : arrayList) {
                if (user.getName().equals(user1.getName()) && user.getPass().equals(user1.getPass())) {
                    //Check if the given username is already in the list or not. If not, it's not unique and we should let the user know.
                    unique = false;
                    break;
                }
            }

            resp.setContentType("text/html");

            if (unique) {
                //If the new credentials are unique, then add the user to the list of users and notify the user of the success.
                arrayList.add(user);
                resp.getWriter().println("Account has succesfully been registered. Go to the <a href=\"/login.html\">login page</a> to try your new account.");
            } else {
                //The user credentials are not unique, so let them know and give them an option to try again.
                resp.getWriter().println("User with that name already exists!");
                resp.getWriter().println(" To try again, <a href=\"/register.html\">go back to registration.</a>");
            }
        }
    }
}
