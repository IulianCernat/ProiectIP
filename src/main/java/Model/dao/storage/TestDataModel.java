package Model.dao.storage;

public class TestDataModel {
        private int id;
        private String input;
        private String output;

    public TestDataModel(int id, String input, String output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    public int getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
