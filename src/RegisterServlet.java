import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Java Servlet that is used to check requested user credentials for registration and storing them if unique.
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private Model model;

    @Override
    public void init() throws ServletException {
        super.init();

        model = (Model) getServletContext().getAttribute("model");
    }

    /**
     * Method that is called by the registration.html file to possibly register a new user with given credentials
     *
     * @param req  is the request from the user's client.
     * @param resp is what the server will respond with to the request.
     * @throws ServletException is an exception thrown when the server encounters any kind of difficulty.
     * @throws IOException      happens when any form of an I/O operation has been interrupted or caused to fail.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Check if they didn't force a POST through a service like Postman that doesn't have any parameters in it.

        boolean notUnique;
        //If there is a user list, check whether the credentials are unique, if yes add the user to the list.
        //Retrieve the userList from the ServletContext.

        //Check if the parameters are empty or null. If they are redirect the user to an error page.
        if (req.getParameter("chosenUsername").isEmpty() || req.getParameter("chosenUsername") == null) {
            resp.sendRedirect("NO.html");
        }
        if (req.getParameter("chosenPassword").isEmpty() || req.getParameter("chosenPassword") == null) {
            resp.sendRedirect("NO.html");
        }
        if (req.getParameter("userType").isEmpty() || req.getParameter("userType") == null) {
            resp.sendRedirect("NO.html");
        }

        User user = new User(req.getParameter("chosenUsername"), req.getParameter("chosenPassword"), req.getParameter("userType"));

        notUnique = model.checkUser(user);

        resp.setContentType("text/html");
        if (!notUnique) {
            //If the new credentials are unique, then add the user to the list of users and notify the user of the success.
            model.getRegisteredUsers().add(user);
            resp.getWriter().println("Account has succesfully been registered. Go to the <a href=\"./login.html\">login page</a> to try your new account.");
        } else {
            //The user credentials are not unique, so let them know and give them an option to try again.
            resp.getWriter().println("User with that name already exists!");
            resp.getWriter().println(" To try again, <a href=\"./register.html\">go back to registration.</a>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Nobody should be able to just force their way into this servlet. This is because the doGet of this servlet shouldn't do anything.
        resp.sendRedirect("./register.html");
    }
}
