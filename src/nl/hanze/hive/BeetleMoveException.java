package nl.hanze.hive;

import nl.hanze.hive.Hive;

/**
 * Created by Robert Ziengs on 19-10-2018.
 */
public class BeetleMoveException extends Hive.IllegalMove {

    public BeetleMoveException(String message){
        super(message);
    }
}
