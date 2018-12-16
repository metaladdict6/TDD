package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class QueenMoveTooFarException extends Hive.IllegalMove {

    public QueenMoveTooFarException(String message) {
        super(message);
    }
}
