package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 19-10-2018.
 */
public class QueenMoveException extends Hive.IllegalMove {

    public QueenMoveException() {
        super();
    }

    public QueenMoveException(String message) {
        super(message);
    }
}
