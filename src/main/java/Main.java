import com.mysql.cj.xdevapi.JsonArray;
import controller.Problem;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.File;
import java.io.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Problem pr = new Problem();

        //pr.testIfFunctioning();
        //System.out.println(pr.getEnuntProblema(1));
        //Document doc    = pr.getTeste(1);

        //File xmlFile = new File("e:\\problem.xml");
        //pr.addProblem(xmlFile);
        //System.out.println(pr.getLastProblemId());
        //System.out.println(pr.getProblemTests(1));

        String message;
        JSONObject json = new JSONObject();

        json.put("test_in", "Alfabet");
        json.put("test_out", "ALFABET");
        json.put("percentage", 10.0 );

        message = json.toString();
        System.out.println(message);
        // pr.addTestToProblem(json,3);
        //System.out.println((pr.getProblemsByGrade(9)).toString());
        //System.out.println(pr.getTestPercentage(1));

    }

}