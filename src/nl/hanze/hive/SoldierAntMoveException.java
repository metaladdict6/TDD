package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class SoldierAntMoveException extends Hive.IllegalMove {
    public SoldierAntMoveException() {
        super();
    }

    public SoldierAntMoveException(String message) {
        super(message);
    }
}
