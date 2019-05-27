package controller.servlets;

import Model.dao.Problem;
import Model.dao.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddProblem")
public class AddProblem extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("userId");
        JSONObject problem = new JSONObject();
        problem.put("title", request.getParameter("titlu"));
        problem.put("statement", request.getParameter("enunt"));
        problem.put("difficulty", request.getParameter("dificultate"));
        problem.put("category", Integer.parseInt(request.getParameter("clasa")));
        problem.put("solution", request.getParameter("solutie"));

        JSONArray testList = new JSONArray();

        for (int i = 1; i <= 3; i++) {
            String output = "output";
            String input = "input";
            String pondere = "pondere";

            JSONObject test = new JSONObject();
            input += i;
            output += i;
            pondere += i;
            test.put("input", request.getParameter(input));
            test.put("output", request.getParameter(output));
            test.put("pondere", request.getParameter(pondere));
            testList.put(test);
        }

        Problem.addProblem(problem, testList);
        User.updateNrOfUploads(userId);

    }

}
