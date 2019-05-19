package Model.dao;

import Model.dao.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public static boolean checkIfUserExists(String username) {
        boolean exist = false;
        String query = "select * from utilizatori where username=? ";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {
            statement.setString(1, username);
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }


    //Trebuie modificata. In baza de date oricum username-ul are constrangere de unicitate
    /*
    public static boolean addUser(String username, String password, String email) {
        String query = "select * from users where username=? ";
        try(Connection myConn = Database.getConnection();
            PreparedStatement statement = myConn.prepareStatement(query);){
            statement.setString(1, username);
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return false; //inserare nereusita.

            statement = myConn.prepareStatement("insert into users (username,password,email) values (?,?,?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; //inserare reusita

}


    //Vine modificata mai tarziua
    /*
    public boolean verifyUserPassword(String username, String password) {
        //returneaza 1 daca username-ul si parola exista in baza de date
        //returneaza -1 daca cele doua nu exista.
        String query = "select count(id) as valid from users WHERE username= ?  and password = ?";
        try(Connection myConn = Database.getConnection();
            PreparedStatement statement = myConn.prepareStatement(query);) {
            int availableId = 0;

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                availableId = Integer.parseInt(rs.getString("valid"));
            }

            if (availableId == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // nu avem in db userul si parola dorita

    }*/

    /**
     * Aceasta functie obtine scorul unui utilizator pentru o anumita problema
     *
     * @param userId    Id-ul utilizatorului care se cauta in baza de date
     * @param problemId Id-ul problemei care se cauta in baza de date
     * @return int Numarul de puncte ale utilizatorului pentru problema
     */
    public static int getUserProblemScore(int userId, int problemId) {
        String query = "select score from points WHERE id_user = ? and id_problem = ?";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {
            statement.setInt(1, userId);
            statement.setInt(2, problemId);
            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) return rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // nu avem punctaj pentru problema data
    }

    /**
     * Aceasta functie este folosita pentru a adauga punctajul unui utilizator la o anumita problema
     *
     * @param userId    utilizatorul care a rezolvat problema
     * @param problemId Id-ul problemei la care se va adauga punctajul
     * @param points    punctajul obtinut de utilizator
     * @return
     */
    public static void setPoints(int userId, int problemId, int points) {

        try {

            Connection myConn = Database.getConnection();
            PreparedStatement statement = myConn.prepareStatement("insert into points (id_user,id_problem,points) values (?,?,?)");
            statement.setInt(1, userId);
            statement.setInt(2, problemId);
            statement.setInt(3, points);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Aceasta functie este folosita pentru a cauta email-ul unui user pe baza id-ului acestuia
     *
     * @param userId este id-ul user-ului pentru care se va cauta email-ul
     * @return email Acesta este email-ul user-ului.    Returneaza null in cazul in care id-ul nu exista in baza de date
     */
    public String getEmail(int userId) {
        String email = null;
        String query = "select email from users where id=? ";
        try (Connection myConn = Database.getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  email;
    }





}