package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 16-12-2018.
 */
public class PlaceQueenBeforeContinuingException extends Hive.IllegalMove {

    public PlaceQueenBeforeContinuingException(String message) {
        super(message);
    }
}
