package Model.dao;

import java.sql.ResultSet;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;
import Model.dao.storage.Database;


public class Problem {

    public static void addProblem(JSONObject problem, JSONObject tests) {
        int idProblema = 0;
        String query = "INSERT INTO `problem`(`title`, `requirement`, `solution`, `category`, `created_at`, `difficulty`) VALUES (?,?,?,?,?,?)";

        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {

            statement.setString(1, problem.getString("tilte"));
            statement.setString(2, problem.getString("requiremets"));
            statement.setString(3, problem.getString("solution"));
            statement.setString(4, problem.getString("category"));
            statement.setString(5, "sysdate()");
            statement.setString(6, problem.getString("difficulty"));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "SELECT `id` FROM `problem` WHERE `title` = ? AND `requirement` = ? AND `solution` = ? AND `category` = ? AND `difficulty` = ?";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setString(1, problem.getString("tilte"));
            statement.setString(2, problem.getString("requiremets"));
            statement.setString(3, problem.getString("solution"));
            statement.setString(4, problem.getString("category"));
            statement.setString(5, problem.getString("difficulty"));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) idProblema = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (idProblema > 0) {// daca am gasit problema incepem sa punem testele
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
     * @param title este titlul problemei pentru care se va cauta id-ul
     * @return problemId . Acesta este id-ul problemei
     */
    public  int getProblemId(String title) {
        int problemId = -1;
        String query = "select id from problem where title=? ";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {
            statement.setString(1, title);
            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) problemId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problemId;
    }

    /**
     * Aceasta functie returneaza un JSONArray cu toate problemele in functie de clasa
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
     * @param title este numele problemei
     * @return JSONObject ce reprezinta problema si contine(id, titlu, categorie, dificultate si un set de teste)
     */
    public JSONObject getProblem (String title){
        JSONObject problem = new JSONObject();
        String query="select p.id, p.title,p.statement,p.category,p.difficulty,t.test_in,t.test_out from problem p inner join problem_test t on p.id = t.id_problem where p.title=? limit 1";

        try {
            Connection myConn = Database.getConnection();
            PreparedStatement statement = myConn.prepareStatement(query);
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                problem.put("id",resultSet.getInt("id"));
                problem.put("title",resultSet.getString("title"));
                problem.put("statement",resultSet.getString("statement"));
                problem.put("category",resultSet.getInt("category"));
                problem.put("difficulty",resultSet.getString("difficulty"));
                problem.put("test_in",resultSet.getString("test_in"));
                problem.put("test_out",resultSet.getString("test_out"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return problem;
    }
}