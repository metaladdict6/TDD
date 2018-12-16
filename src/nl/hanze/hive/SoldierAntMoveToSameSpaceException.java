package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class SoldierAntMoveToSameSpaceException extends Hive.IllegalMove {

    public SoldierAntMoveToSameSpaceException(String message) {
        super(message);
    }
}
