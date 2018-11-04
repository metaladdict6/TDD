package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class QueenMoveException extends Hive.IllegalMove {

    public QueenMoveException() {
        super();
    }

    public QueenMoveException(String message) {
        super(message);
    }
}
