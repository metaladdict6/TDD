package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 16-12-2018.
 */
public class GrassHopperMoveToSamePlaceException extends Hive.IllegalMove {

    public GrassHopperMoveToSamePlaceException(String message) {
        super(message);
    }
}
