package Model.dao;

import Model.dao.storage.Database;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.ArrayList;

import static com.sun.org.apache.bcel.internal.classfile.Utility.toHexString;


public class User {

    public static boolean checkIfUserExists(String username) {
        boolean exist = false;
        String query = "select * from users where username=? ";
        try (Connection myConn = new Database().getConnection();
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

    /**
     * Aceasta funtie adauga in baza de date utilizatorul inregistrat
     *
     * @param username sub forma de string
     * @param password sub forma de string
     * @param email    sub forma de string
     */

    public static void addUser(String username, String password, String email) {
        String query = "INSERT INTO users(username, password, email, salt, solved_problems_no, uploaded_problems_no, points_no) VALUES (?,?,?,?,0,0,0) ";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query);) {

            String salt = getSalt();
            String passwordHash = md5Hash(password, salt);

            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, email);
            statement.setString(4, salt);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aceasta functie face un hash din parola primita ca argument pe baza unui salt tot primit ca argument
     *
     * @param password - parola sub forma de string
     * @param salt     - saltul sub forma de string
     * @return un hash returnat ca string
     */

    public static String md5Hash(String password, String salt) {
        byte[] saltByt = salt.getBytes();
        String passwordHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(saltByt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder passBuilder = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                passBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = passBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }

    /**
     * @return Returneaza un string de control pentru criptarea parolei
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */

    protected static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    /**
     * Aceasta functie obtine scorul unui utilizator pentru o anumita problema
     *
     * @param userId    Id-ul utilizatorului care se cauta in baza de date
     * @param problemId Id-ul problemei care se cauta in baza de date
     * @return int Numarul de puncte ale utilizatorului pentru problema
     */
    public static int getUserProblemScore(int userId, int problemId) {
        String query = "select score from points WHERE id_user = ? and id_problem = ?";
        try (Connection myConn = new Database().getConnection();
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
     */
    public static void setPoints(int userId, int problemId, int points) {

        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement("insert into points (id_user,id_problem,points) values (?,?,?)");) {
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
    public static String getEmail(int userId) {
        String email = null;
        String query = "select email from users where id=? ";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    /**
     * Aceasta functie este folosita pentru a cauta parola unui user pe baza id-ului acestuia
     *
     * @param username este username-ul user-ului pentru care se va cauta parola
     * @return password Aceasta este parola user-ului.    Returneaza null in cazul in care id-ul nu exista in baza de date
     */
    public static String getPassword(String username) {
        String password = null;
        String query = "select password from users where username=? ";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) password = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }


    /**
     * Aceasta functie este folosita pentru a cauta punctajul unui user pe baza id-ului acestuia
     *
     * @param userId este id-ul user-ului pentru care se va cauta punctajul
     * @return points Acesta este punctajul user-ului.   Returneaza -1 in cazul in care id-ul nu exista in baza de date
     */
    public static int getPoints(int userId) {
        int points = -1;
        String query = "select points_no from users where id=? ";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) points = rs.getInt("points_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    /**
     * Aceasta functie este folosita pentru a returna id-ul unui utilizator
     *
     * @param username este username-ul user-ului pentru care se va cauta id-ul
     * @return id Acesta este id-ul user-ului.    Returneaza -1 in cazul in care username-ul nu exista in baza de date
     */
    public static int getId(String username) {
        int id = -1;
        String query = "select id from users where username=?";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Aceasta functie este folosita pentru a returna numarul de probleme incarcate de utilizator
     *
     * @param userId este id-ul user-ului pentru care se va cauta numarul de probleme incarcate
     * @return count Acesta este numarul de probleme incarcate.    Returneaza 0 in cazul in care user-ul nu are nicio problema incarcata
     */
    public static int getNrOfUploadedProblems(int userId) {
        int count = 0;
        String query = "select uploaded_problems_no from users where id = ?";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) count = rs.getInt("uploaded_problems_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    /**
     * Aceasta functie returneaza un JSONObject care contine toate detaliile despre user
     *
     * @param userId este id-ul utilizatorului
     * @return JSONObject ce reprezinta user-ul si contine(id, username, email, nr. de probleme rezolvate, nr. de probleme incarcate si numarul de puncte)
     */
    public static JSONObject getUserPublicData(int userId) {
        JSONObject user = new JSONObject();
        String query = "SELECT id,username,email,solved_problems_no,uploaded_problems_no,points_no from users where id=?";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.put("id", resultSet.getInt("id"));
                user.put("username", resultSet.getString("username"));
                user.put("email", resultSet.getString("email"));
                user.put("solved_problems_no", resultSet.getInt("solved_problems_no"));
                user.put("uploaded_problems_no", resultSet.getInt("uploaded_problems_no"));
                user.put("points_no", resultSet.getInt("points_no"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Aceasta functie returneaza un JSONArray cu toti utilizatorii in ordinea punctajelor
     *
     * @return JSONArray ce contine utilizatorii fiecare element dinarray avand id-ul, username-ul si numarul de puncte al fiecarui utilizator
     */
    public static JSONArray getUsersOrderedByScore() {
        JSONArray array = new JSONArray();
        String query = "SELECT id,username,points_no from users order by points_no desc";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JSONObject user = new JSONObject();
                user.put("id", resultSet.getInt("id"));
                user.put("username", resultSet.getString("username"));
                user.put("points_no", resultSet.getInt("points_no"));
                array.put(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * Aceasta functie este folosita pentru a returna numarul de probleme rezolvate de un anumit utilizator
     *
     * @param userId este id-ul user-ului pentru care se va cauta numarul de probleme rezolvate
     * @return count Acesta este numarul de probleme rezolvate.    Returneaza 0 in cazul in care user-ul nu are nicio problema rezolvata
     */
    public static int getSolvedProblemsNr(int userId) {
        int count = 0;
        String query = "select solved_problems_no from users where id = ?";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) count = rs.getInt("solved_problems_no ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    /**
     * Aceasta functie este folosita pentru obtinerea salt-ului unui utilizator
     *
     * @param userName este numele de utilizator al user-ului
     * @return salt
     */
    public static String getUserSalt(String userName) {
        String userSalt = null;
        String query = "select salt from users where username = ?";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) userSalt = rs.getString("salt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSalt;

    }

    /**
     * Aceasta functie este folosita pentru a returna id ul problemelor care
     * au fost rezolvate de catre un utilizator primind puntaj maxim
     *
     * @param userId este  id-ul utilizatorului
     * @return JSONArray [{id}]
     */
    public static JSONArray getSolvedOrTriedProblems(int userId, boolean fullScoreFlag) {
        JSONArray jsonArray = new JSONArray();
        String query;
        if (fullScoreFlag)
            query = "select id_problem, points, title from points join problem " +
                    "on points.id_problem = problem.id and id_user = ? and points = ?";
        else
            query = "select id_problem, points, title from points join problem " +
                    "on points.id_problem = problem.id and id_user = ? and points < ?";

        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, 100);
            try (ResultSet resultSet = statement.executeQuery();) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * Aceasta functie actualizeaza punctajul utilizatorului
     *
     * @param userId    reprezinta id-ul utilizatorului pentru care se va face actualizarea
     * @param newPoints reprezinta punctele acumulate in urma unei probleme adaugate.
     */
    public static void addObtainedPoints(int userId, int newPoints) throws SQLException {

        PreparedStatement statement = null;
        String query = "update users set points_no = points_no + ? where id=?  ";
        try {
            Connection myConn = new Database().getConnection();
            statement = myConn.prepareStatement(query);
            statement.setInt(1, newPoints);
            statement.setInt(2, userId);
            statement.executeUpdate();

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

    /**

     *Aceasta functie incrementeaza numarul de probleme rezolvate de user
     * @param userId este id-ul user-ului pentru care se va face actualizarea
     *
     */
        public static void updateNrOfSolvedProblmes(int userId) throws SQLException {

            PreparedStatement statement = null;
            String query = "update users set solved_problems_no = solved_problems_no + 1 where id=?  ";
            try {
                Connection myConn = new Database().getConnection();
                statement = myConn.prepareStatement(query);
                statement.setInt(1, userId);
                statement.executeUpdate();

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



    /**

     * Aceasat functie incrementeaza numarul de probleme uploadate de user
     * @param userId int
     */

    public static void updateNrOfUploads(int userId) {

        int numberOfUploads = getNrOfUploadedProblems(userId);
        numberOfUploads++;
        String query = "UPDATE `users` SET `uploaded_problems_no`= ? WHERE `id` = ?";
        try (Connection myConn = new Database().getConnection();
             PreparedStatement statement = myConn.prepareStatement(query)) {
            statement.setInt(1,numberOfUploads);
            statement.setInt(2,userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


	/** Aceasta functie actualizeaza punctajul unui utilizator pentru o anumita problema
     *
             * @param userId    reprezinta id-ul utilizatorului pentru care se va face actualizarea
     * @param problemId reprezinta id-ul problemei
     * @param newPoints reprezinta punctele acumulate in urma rezolvarii problemei.
            */

    public static void updateProblemScore(int userId, int problemId, int newPoints) {
        try {

            Connection conn = new Database().getConnection();

            // create the java mysql update preparedstatement
            String query = "update points set points = ? where id_user = ? and id_problem = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, newPoints);

            preparedStmt.setInt(2, userId);
            preparedStmt.setInt(3, problemId);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
