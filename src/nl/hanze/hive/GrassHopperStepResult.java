package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class GrassHopperStepResult {

    private int steps;

    private boolean goesOverPiece;

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public boolean isGoesOverPiece() {
        return goesOverPiece;
    }

    public void setGoesOverPiece(boolean goesOverPiece) {
        this.goesOverPiece = goesOverPiece;
    }

    public GrassHopperStepResult(int steps, boolean goesOverPiece) {
        this.steps = steps;
        this.goesOverPiece = goesOverPiece;
    }
}
