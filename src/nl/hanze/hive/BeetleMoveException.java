package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class BeetleMoveException extends Hive.IllegalMove {
    public BeetleMoveException(String message){
        super(message);
    }
}
