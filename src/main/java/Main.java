import Model.dao.Problem;
import Model.dao.Test;
import Model.dao.User;
import Model.dao.storage.TestDataModel;
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
        Test test = new Test();
        ArrayList<TestDataModel> tests = test.getTests(1);

        for (TestDataModel testModel : tests)
        {
            System.out.println(testModel.getId());
            System.out.println(testModel.getInput());
            System.out.println(testModel.getOutput());
            System.out.println("\n");
        }


    }

}