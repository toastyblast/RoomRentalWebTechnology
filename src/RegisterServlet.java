import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    /**
     * Method to register users.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean unique = true;

        //Check if there is a user list, if not create one.
        if (req.getSession().getServletContext().getAttribute("userList") == null) {
            ArrayList<User> arrayList = new ArrayList();
            req.getSession().getServletContext().setAttribute("userList", arrayList);
        }

        //If there is a user list, check whether the credentials are unique, if yes add the user to the list.
        if (req.getSession().getServletContext().getAttribute("userList") != null) {

            //Retrieve the userList from the ServletContext.
            Object myContextParam = req.getSession().getServletContext().getAttribute("userList");
            ArrayList<User> arrayList = (ArrayList<User>) myContextParam;
            User user = new User(req.getParameter("chosenUsername"), req.getParameter("chosenPassword"), req.getParameter("userType"));

            for (int i = 0; arrayList.size() > i; i++) {
                User user1 = arrayList.get(i);

                if (user.getName().equals(user1.getName()) && user.getPass().equals(user1.getPass())) {
                    unique = false;
                }
            }

            if (unique) {
                arrayList.add(user);
                req.getSession().getServletContext().setAttribute("userList", arrayList);
                resp.getWriter().println(myContextParam);
            } else {
                resp.getWriter().println("User already exists!");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
