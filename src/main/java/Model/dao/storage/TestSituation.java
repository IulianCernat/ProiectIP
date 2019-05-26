package Model.dao.storage;

public class TestSituation {
    private int index;
    private String evaluationMessage;
    private int originalScore;
    private int obtainedScore;

    public TestSituation(int index, String evaluationMessage, int originalScore, int obtainedScore) {
        this.index = index;
        this.evaluationMessage = evaluationMessage;
        this.originalScore = originalScore;
        this.obtainedScore = obtainedScore;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public String getEvaluationMessage() {
        return evaluationMessage;
    }

    public void setEvaluationMessage(String evaluationMessage) {
        this.evaluationMessage = evaluationMessage;
    }

    public int getOriginalScore() {
        return originalScore;
    }

    public void setOriginalScore(int originalScore) {
        this.originalScore = originalScore;
    }

    public int getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScor(int obtainedScor) {
        this.obtainedScore = obtainedScor;
    }
}
