package controller.servlets;

import Model.dao.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        try {
            if (User.checkIfUserExists(typedUsername)) {
                String salt = User.getUserSalt(typedUsername);
                //String stringSalt = toHexString(salt.getBytes());
                byte[] passwordHash = User.pbkdf2(typedPassword.toCharArray(), salt.getBytes(), User.getPbkdf2Iterations(), User.getHashBytes());
                String passwordHex = toHexString(passwordHash);
                String  databasePassword = User.getPassword(typedUsername);//.getBytes();
                System.out.println(passwordHex);
                System.out.println(databasePassword);

                if(databasePassword.equals(passwordHex))
                {
                    loginflag = true;
                    response.sendRedirect("./html/cont.html");
                }
                else
                    response.sendRedirect("index.html");
            }
            else
                response.sendRedirect("index.html");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

}
