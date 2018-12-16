package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class GrassHopperMoveOverUnOccupiedException extends Hive.IllegalMove {

    public GrassHopperMoveOverUnOccupiedException(String message){
        super(message);
    }
}
