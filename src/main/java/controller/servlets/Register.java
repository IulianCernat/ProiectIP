package controller.servlets;

import Model.dao.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("Password");
        String repeatPass = request.getParameter("repeat");

        if(!password.equals(repeatPass)) response.sendRedirect("./html/signUp.html");
            else {
            User.addUser(username, password, email);
            response.sendRedirect("index.html");
        }
    }
}
