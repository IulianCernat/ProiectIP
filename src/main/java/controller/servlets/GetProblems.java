package controller.servlets;

import Model.dao.Problem;
import org.json.JSONArray;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetProblems extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Problem pr = new Problem();
        JSONArray problemList =  pr.getProblemsByGrade(Integer.parseInt(request.getParameter("grade")));
        request.setAttribute("problemList", problemList);
        RequestDispatcher rd = request.getRequestDispatcher("jsp/problemeAfisate.jsp");
        rd.forward(request, response);

    }

}
