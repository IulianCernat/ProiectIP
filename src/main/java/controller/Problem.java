package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

import storage.Database;


public class Problem {


    public int getUserProblemScore(int userId, int problemId) {
        String query = "select score from points WHERE id_user = ? and id_problem = ?";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {
            statement.setInt(1, userId);
            statement.setInt(2, problemId);
            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) return rs.getInt("punctaj");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // nu avem punctaj pentru problema data
    }

    public HashMap<Integer, HashMap<String, String>> getProblemTests(int problemId) {
        String query = "select id, test_in as input, test_out as output from problem_test where id_problem = ?";
        HashMap<Integer, HashMap<String, String>> tests = new HashMap<>();
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {
            statement.setInt(1, problemId);
            try (ResultSet rs = statement.executeQuery();) {

                HashMap<String, String> inputOutputValues = new HashMap<>();
                if (rs.next()) {
                    inputOutputValues.put("input", rs.getString("input"));
                    inputOutputValues.put("output", rs.getString("output"));
                    tests.put(rs.getInt("id"), inputOutputValues);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
}