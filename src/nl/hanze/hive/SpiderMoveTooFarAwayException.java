package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class SpiderMoveTooFarAwayException extends Hive.IllegalMove {

    public SpiderMoveTooFarAwayException(String message) {
        super(message);
    }
}
