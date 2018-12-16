package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class GrassHopperMoveToSamePlaceException extends Hive.IllegalMove {

    public GrassHopperMoveToSamePlaceException(String message) {
        super(message);
    }
}
