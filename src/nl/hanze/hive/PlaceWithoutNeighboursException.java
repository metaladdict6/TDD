package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class PlaceWithoutNeighboursException extends Hive.IllegalMove {

    public PlaceWithoutNeighboursException(String message) {
        super(message);
    }
}
