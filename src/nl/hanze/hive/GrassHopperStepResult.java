package nl.hanze.hive;

public class GrassHopperStepResult {

    public int getSteps() {
        return Steps;
    }

    public void setSteps(int steps) {
        Steps = steps;
    }

    public boolean isGoesOverPiece() {
        return goesOverPiece;
    }

    public void setGoesOverPiece(boolean goesOverPiece) {
        this.goesOverPiece = goesOverPiece;
    }

    private int Steps;

    private boolean goesOverPiece;

    public GrassHopperStepResult(int steps, boolean goesOverPiece) {
        Steps = steps;
        this.goesOverPiece = goesOverPiece;
    }
}
