package Model.dao;

import java.sql.ResultSet;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import Model.dao.storage.Database;


public class Problem {

    /**
     * Aceasta functie este folosita pentru a insera in baza de date o problema si testele pentru aceasta
     *
     * @param problem este un obiect JSON ce contrine 5 campuri: "title", "statement", "solution", "category", "difficulty"
     * @param tests   este un obiect JSON ce contine 3 campuri "test_in", "test_out"m "percentage"
     */

    public static void addProblem(JSONObject problem, JSONObject tests) {
        int idProblema = 0;
        String query = "INSERT INTO problem(title, statement, solution, category, created_at, difficulty) VALUES (?,?,?,?,?,?)";

        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {

            statement.setString(1, problem.getString("title"));
            statement.setString(2, problem.getString("statement"));
            statement.setString(3, problem.getString("solution"));
            statement.setString(4, problem.getString("category"));
            statement.setString(5, "sysdate()");
            statement.setString(6, problem.getString("difficulty"));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (getProblemId(problem.getString("title")) != -1) {// daca am gasit problema incepem sa punem testele
            JSONArray testsArray = tests.getJSONArray("test");
            JSONObject test;

            for (int testNr = 0; testNr < testsArray.length(); testNr++) {
                test = testsArray.getJSONObject(testNr);
                Test.addTestToProblem(test, idProblema);
            }
        } else System.out.println("Eroare la inserarea problemei in tabela");
    }


    /**
     * Aceasta functie este folosita pentru a cauta id-ul unei probleme cu un anumit titlu
     *
     * @param title este titlul problemei pentru care se va cauta id-ul
     * @return problemId . Acesta este id-ul problemei
     */
    public static int getProblemId(String title) {
        int problemId = -1;
        String query = "select id from problem where title=? ";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setString(1, title);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) problemId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problemId;
    }

    /**
     * Aceasta functie returneaza un JSONArray cu toate problemele in functie de clasa
     *
     * @param grade este clasa dupa care se face filtrarea problemelor
     * @return JSONArray - Fiecare element din array are 2 chei: 'id' (id-ul problemei din baza de date) si 'problem' (care contine informatiile concrete despre fiecare problema: titlu,dataintroducerii,dificultate si categorie)
     */
    public JSONArray getProblemsByGrade(int grade) {
        JSONArray jsonArray = new JSONArray();
        String query = "select id,title,created_at,difficulty,category from problem where category=? ";
        try {
            Connection myConn = Database.getConnection();
            PreparedStatement statement = myConn.prepareStatement(query);
            statement.setInt(1, grade);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int total_rows = resultSet.getMetaData().getColumnCount();

                JSONObject mainObj = new JSONObject();
                JSONObject obj = new JSONObject();
                for (int i = 0; i < total_rows; i++) {
                    obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
                }
                mainObj.put("problem", obj);
                jsonArray.put(mainObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * Aceasta functie returneaza un JSONObject care contine toate detaliile despre problema si un set de input-output
     *
     * @param title este numele problemei
     * @return JSONObject ce reprezinta problema si contine(id, titlu, categorie, dificultate si un set de teste)
     */
    public JSONObject getProblem(String title) {
        JSONObject problem = new JSONObject();
        String query = "select p.id, p.title,p.statement,p.category,p.difficulty,t.test_in,t.test_out from problem p inner join problem_test t on p.id = t.id_problem where p.title=? limit 1";

        try {
            Connection myConn = Database.getConnection();
            PreparedStatement statement = myConn.prepareStatement(query);
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                problem.put("id", resultSet.getInt("id"));
                problem.put("title", resultSet.getString("title"));
                problem.put("statement", resultSet.getString("statement"));
                problem.put("category", resultSet.getInt("category"));
                problem.put("difficulty", resultSet.getString("difficulty"));
                problem.put("test_in", resultSet.getString("test_in"));
                problem.put("test_out", resultSet.getString("test_out"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return problem;
    }


    /**
     * Aceasta functie returneaza un int care reprezinta scorul unei probleme.
     *
     * @param map Un HashMap care contine elemente de tipul [id_test ->boolean]
     * @return int care reprezinta scorul calculat pentru testele trecute (care au true) .
     */
    public int calculateScore(HashMap<Integer, Boolean> map) throws SQLException {

        PreparedStatement statement = null;
        int score = 0, percentage = 0;

        for (Map.Entry element : map.entrySet()) {
            int key = (int) element.getKey();
            boolean value = (boolean) element.getValue();


            if (value) {
                String query = "select percentage from problem_test where id=? ";
                try {
                    Connection myConn = Database.getConnection();
                    statement = myConn.prepareStatement(query);
                    statement.setInt(1, key);
                    statement.executeQuery();
                    ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                        percentage = rs.getInt("percentage");
                        score += percentage;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();

                        } catch (SQLException ex) {
                            System.out.println(ex);
                            
                        }
                    }
                }
            }

        }

        return score;
    }


}