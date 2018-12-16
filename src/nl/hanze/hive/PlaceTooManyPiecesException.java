package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class PlaceTooManyPiecesException extends Hive.IllegalMove {

    public PlaceTooManyPiecesException(String message) {
        super(message);
    }
}
