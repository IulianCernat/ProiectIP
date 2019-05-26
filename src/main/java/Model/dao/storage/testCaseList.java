package Model.dao.storage;

import java.util.ArrayList;

public class testCaseList{


    private ArrayList<String> input = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();
    private ArrayList<Integer> testsId = new ArrayList<>();
    private int testCount;

    public testCaseList(){

        testCount=0;

    }


    public void addTestCase(Integer id, String input_str, String output_str){

        testsId.add(id);
        input.add(input_str);
        output.add(output_str);

        testCount++;

    }


    public String getTestInput(Integer testIndex){

        //int i = id_test.indexOf(id);
        //return input.get(i);
        return input.get(testIndex);

    }


    public String getTestOutput(Integer testIndex){

        //int i = id_test.indexOf(id);
        //return output.get(i);

        return output.get(testIndex);

    }

    public int getTestId(Integer testIndex){
        return testsId.get(testIndex);
    }

    public int getTestCount(){

        return testCount;

    }

    public void flush(){

        input.clear();
        output.clear();
        testsId.clear();
        testCount=0;

    }

}