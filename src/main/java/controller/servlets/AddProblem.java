package controller.servlets;

import Model.dao.Problem;
import Model.dao.User;
import org.json.JSONArray;
import org.json.JSONException;
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

        final int MAX_TEST_NUMBER = 100;
        HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("userId");
        JSONObject problem = new JSONObject();
        problem.put("title", request.getParameter("titlu"));
        problem.put("statement", request.getParameter("enunt"));
        problem.put("difficulty", request.getParameter("dificultate"));
        problem.put("category", Integer.parseInt(request.getParameter("clasa")));
        problem.put("solution", request.getParameter("solutie"));

        JSONArray testList = new JSONArray();

        try {
            for (int i = 1; i <= MAX_TEST_NUMBER; i++) {
                String output = "output";
                String input = "input";
                String pondere = "pondere";

                JSONObject test = new JSONObject();
                input += i;
                output += i;
                pondere += i;
                String stringIn = request.getParameter(input);
                String stringOut = request.getParameter(output);
                String stringPr = request.getParameter(pondere);

                if(stringIn == null || stringOut ==null || stringPr == null) break;
                test.put("input", stringIn);
                test.put("output", stringOut);
                test.put("pondere", stringPr);
                testList.put(test);
            }
        } catch (JSONException e) {
            System.out.println("JSONException in AddProblem!");;
        }

        Problem.addProblem(problem, testList);
        User.updateNrOfUploads(userId);

        response.sendRedirect("GetUserProfile");

    }

}
