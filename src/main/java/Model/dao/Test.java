package Model.dao;

import Model.dao.storage.testCaseList;
import Model.dao.storage.Database;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {

    public static testCaseList getProblemTests(int problemId) {
        String query = "select id, test_in as input, test_out as output from problem_test where id_problem = ?";
        testCaseList testList = new testCaseList();
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {
            statement.setInt(1, problemId);
            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    testList.addTestCase(rs.getInt("id"),
                            rs.getString("input"),
                            rs.getString("output"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testList;
    }

    /**
     * Aceasta functie opbtine poderea unui test
     *
     * @param testId Id-ul testului
     * @return Ponderea unui test sau -1 daca id-ul testului nu exista
     */
    public static int getTestPercentage(int testId) {
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement("select percentage as percent from problem_test WHERE id = ? ");) {
            statement.setInt(1, testId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("percent");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // nu avem procent (nu exista testul)
    }

    /**
     * Aceasta functie este folosita pentru a adauga teste la o anumita problema
     *
     * @param problemId Id-ul problemei la care se va dauga testul
     * @param obj       este obiectul de tip JSON care contine testele input, output si procentajul
     */
    public static void addTestToProblem(JSONObject obj, int problemId) {
        String test_in = (String) obj.get("test_in");
        String test_out = (String) obj.get("test_out");
        Double percentage = (Double) obj.get("percentage");

        String query = "insert into problem_test (id_problem , test_in, test_out, percentage) values(?,?,?,?)";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, problemId);
            statement.setString(2, test_in);
            statement.setString(3, test_out);
            statement.setDouble(4, percentage);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
