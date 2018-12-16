package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class QueenMoveToOccupiedSpaceException extends Hive.IllegalMove {

    public QueenMoveToOccupiedSpaceException(String message) {
        super(message);
    }
}
