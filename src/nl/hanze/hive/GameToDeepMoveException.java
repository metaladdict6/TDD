package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class GameToDeepMoveException extends Hive.IllegalMove {

    public GameToDeepMoveException(String message) {
        super(message);
    }
}
