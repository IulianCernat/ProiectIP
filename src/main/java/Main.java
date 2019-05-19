import Model.dao.Problem;
import Model.dao.User;
import org.json.JSONObject;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
        //Problem pr = new Problem();
        // System.out.println(pr.getProblemId("Numere consecutive"));
        //System.out.println(pr.getProblem("Numar maxim"));



       // User pr2 = new User();

       //System.out.println(pr2.getEmail(1));

        System.out.println(pr2.getUserPublicData(1));

        


        //pr.testIfFunctioning();
        //System.out.println(pr.getEnuntProblema(1));
        //Document doc    = pr.getTeste(1);

        //File xmlFile = new File("e:\\problem.xml");
        //pr.addProblem(xmlFile);
        //System.out.println(pr.getLastProblemId());
        //System.out.println(pr.getProblemTests(1));

        /*String message;
        JSONObject json = new JSONObject();

        json.put("test_in", "Alfabet");
        json.put("test_out", "ALFABET");
        json.put("percentage", 10.0 );

        message = json.toString();
        System.out.println(message);*/
        // pr.addTestToProblem(json,3);
        //System.out.println((pr.getProblemsByGrade(9)).toString());
        //System.out.println(pr.getTestPercentage(1));

    }

}