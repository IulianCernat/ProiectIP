/*package controller.servlets;

import Model.dao.Problem;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProblem extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Problem pr = new Problem();
        String title = request.getParameter("title");
        System.out.println("titlue problema" + title);
        JSONObject problem = pr.getProblem(title);
        request.setAttribute("problem", problem);
        RequestDispatcher rd = request.getRequestDispatcher("jsp/problema.jsp");
        rd.forward(request, response);

    }
}
*/