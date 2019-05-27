import Model.dao.Problem;
import Model.dao.Test;
import Model.dao.User;
import Model.dao.storage.testCaseList;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws SQLException {
        System.out.println(User.getSolvedOrTriedProblems(1, false));
        System.out.println(User.getUserPublicData(1));
    }

}