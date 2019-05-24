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
        testCaseList tests = Test.getProblemTests(1);
        System.out.println(tests.getTestInput(1));
        System.out.println(tests.getTestOutput(1));

        System.out.println(tests.getTestInput(2));
        System.out.println(tests.getTestOutput(2));
        }

}