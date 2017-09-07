import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        user user = new user(request.getParameter("username"), request.getParameter("password"), request.getParameter("userType"));
        boolean correctInfo = false;

        if (request.getSession().getServletContext().getAttribute("userList") == null){
            response.sendRedirect("invalidcredentials.html");
        }else {
            Object myContextParam = request.getSession().getServletContext().getAttribute("userList");
            ArrayList<user> users = (ArrayList<user>) myContextParam;
            for (int i = 0 ; users.size() > i ; i++){
                user user1 = users.get(i);
                if (user.getName().equals(user1.getName()) && user.getPass().equals(user1.getPass())) {
                    correctInfo = true;
                    user = user1;
                }
            }
            if (!correctInfo){
                response.sendRedirect("invalidcredentials.html");
            } else if (correctInfo){
                request.getSession().getServletContext().setAttribute("currentUser", user);
                if (user.getOccupation().equals("tenant")){
                    RequestDispatcher dispatcher=getServletContext().getRequestDispatcher( "/WEB-INF/tenant.html" );
                    dispatcher.forward( request, response );
                } else if (user.getOccupation().equals("landlord")){
                    RequestDispatcher dispatcher=getServletContext().getRequestDispatcher( "/WEB-INF/addroom.html" );
                    dispatcher.forward( request, response );
                }
                response.getWriter().println("ok");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean unique = true;
        if (req.getSession().getServletContext().getAttribute("userList") == null){
            ArrayList<user> arrayList = new ArrayList();
            req.getSession().getServletContext().setAttribute("userList", arrayList);
        }

        if (req.getSession().getServletContext().getAttribute("userList") != null){
            Object myContextParam = req.getSession().getServletContext().getAttribute("userList");
            ArrayList<user> arrayList = (ArrayList<user>) myContextParam;
            user user = new user(req.getParameter("chosenUsername"), req.getParameter("chosenPassword"), req.getParameter("userType"));

            for (int i = 0 ; arrayList.size() > i ; i++){
                user user1 = arrayList.get(i);
                if (user.getName().equals(user1.getName()) && user.getPass().equals(user1.getPass())){
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

    @Override
    public void destroy() {
        super.destroy();

        //Anything else than the super...
    }
}
