package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 19-10-2018.
 */
public class SoldierAntMoveException extends Hive.IllegalMove {

    public SoldierAntMoveException() {
        super();
    }

    public SoldierAntMoveException(String message) {
        super(message);
    }
}
