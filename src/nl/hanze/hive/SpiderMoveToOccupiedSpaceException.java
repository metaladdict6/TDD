package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class SpiderMoveToOccupiedSpaceException extends Hive.IllegalMove {

    public SpiderMoveToOccupiedSpaceException(String message) {
        super(message);
    }
}
