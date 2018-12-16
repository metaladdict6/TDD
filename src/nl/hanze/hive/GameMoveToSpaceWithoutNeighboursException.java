package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class GameMoveToSpaceWithoutNeighboursException extends Hive.IllegalMove {

    public GameMoveToSpaceWithoutNeighboursException(String message) {
        super(message);
    }
}
