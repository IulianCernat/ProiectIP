package controller.servlets;

import Model.dao.Problem;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet(name = "GetProblems")
public class GetProblems extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONArray problems = null;
        Problem pr = new Problem();
        out.println("<html><head></head><body><ul><li>");
            problems = pr.getProblemsByGrade(Integer.parseInt(request.getParameter("grade")));
            for (int i = 0; i < problems.length(); i++) {
                JSONObject jsonobject = problems.getJSONObject(i);
                int id = jsonobject.getInt("id");
                JSONObject problem = jsonobject.getJSONObject("problem");
                out.println(problem.length());
                out.println("</li><li>");
                out.println(problem.getString("title"));
                out.println("</li><li>");
                out.println(problem.getString("difficulty"));
                out.println("</li><li>");
                out.println(problem.getInt("category"));
                out.println("</li><li>");
                out.println(problem.get("created_at"));

            }


        out.println("</li></ul></body></html>");
    }

}
