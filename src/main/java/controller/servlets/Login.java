package controller.servlets;

import Model.dao.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static com.sun.org.apache.bcel.internal.classfile.Utility.toHexString;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String typedUsername = request.getParameter("username");
        String typedPassword = request.getParameter("password");
        boolean loginflag = false;
        if (User.checkIfUserExists(typedUsername)) {
            String salt = User.getUserSalt(typedUsername);
            String passwordHash = User.md5Hash(typedPassword, salt);
            String  databasePassword = User.getPassword(typedUsername);//.getBytes();
            //System.out.println(passwordHash);
            //System.out.println(databasePassword);

            if(databasePassword.equals(passwordHash))
            {
                loginflag = true;
                HttpSession session = request.getSession(true);
                //if(session.isNew()) session.setAttribute("username", typedUsername);
                //else System.out.println("Din sesiune : " + session.getAttribute("username"));
                session.setAttribute("username", typedUsername); // salvez in sesiune usernameul celui ce s-a conectat

                response.sendRedirect("./jsp/cont.jsp");
            }
            else
                response.sendRedirect("index.html");
        }
        else
            response.sendRedirect("./html/signUp.html");

    }

}
