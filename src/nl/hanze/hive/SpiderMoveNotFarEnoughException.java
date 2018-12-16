package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 16-12-2018.
 */
public class SpiderMoveNotFarEnoughException extends Hive.IllegalMove {

    public SpiderMoveNotFarEnoughException(String message) {
        super(message);
    }
}
