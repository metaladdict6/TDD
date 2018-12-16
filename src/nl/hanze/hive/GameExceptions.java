package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 16-12-2018.
 */
public class GameExceptions {
    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GameMoveBeforeQueenPlacement extends Hive.IllegalMove {

        public GameMoveBeforeQueenPlacement(String message){
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GameMoveToSpaceWithoutNeighboursException extends Hive.IllegalMove {

        public GameMoveToSpaceWithoutNeighboursException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GameToDeepMoveException extends Hive.IllegalMove {

        public GameToDeepMoveException(String message) {
            super(message);
        }
    }
}
