package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class PlaceOnExistingPieceException extends Hive.IllegalMove {

    public PlaceOnExistingPieceException(String message){
        super(message);
    }
}
