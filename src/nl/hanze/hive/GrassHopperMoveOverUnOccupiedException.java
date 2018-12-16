package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 16-12-2018.
 */
public class GrassHopperMoveOverUnOccupiedException extends Hive.IllegalMove {

    public GrassHopperMoveOverUnOccupiedException(String message){
        super(message);
    }
}
