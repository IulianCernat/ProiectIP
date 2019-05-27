package controller.servlets;

import Model.dao.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetUserProfile")
public class GetUserProfile extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer)request.getSession(false).getAttribute("userId");
        request.setAttribute("userProfile", User.getUserPublicData(userId));
        request.setAttribute("solvedProblems", User.getSolvedOrTriedProblems(userId, true));
        request.setAttribute("triedProblems", User.getSolvedOrTriedProblems(userId,false));
        RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/cont.jsp");
        dispatcher.forward(request, response);
    }

}
