package nl.hanze.hive;

/**
 * Created by Robert Ziengs on 16-12-2018.
 */
public class PlaceException {
    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class PlaceNextToOpponentException extends Hive.IllegalMove {

        public PlaceNextToOpponentException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class PlaceOnExistingPieceException extends Hive.IllegalMove {

        public PlaceOnExistingPieceException(String message){
            super(message);
        }
    }

    /**
     * Created by Robert Ziengs on 16-12-2018.
     */
    public static class PlaceQueenBeforeContinuingException extends Hive.IllegalMove {

        public PlaceQueenBeforeContinuingException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class PlaceTooManyPiecesException extends Hive.IllegalMove {

        public PlaceTooManyPiecesException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class PlaceWithoutNeighboursException extends Hive.IllegalMove {

        public PlaceWithoutNeighboursException(String message) {
            super(message);
        }
    }
}
