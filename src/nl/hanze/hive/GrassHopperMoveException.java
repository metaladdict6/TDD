package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class GrassHopperMoveException extends Hive.IllegalMove {
    public GrassHopperMoveException(String message) {
        super(message);
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GrassHopperMoveOnSquareException extends Hive.IllegalMove {

        public GrassHopperMoveOnSquareException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GrassHopperMoveOverUnOccupiedException extends Hive.IllegalMove {

        public GrassHopperMoveOverUnOccupiedException(String message){
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GrassHopperMoveToOccupiedSquare extends Hive.IllegalMove {

        public GrassHopperMoveToOccupiedSquare(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GrassHopperMoveToSamePlaceException extends Hive.IllegalMove {

        public GrassHopperMoveToSamePlaceException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class GrassHopperNotMovingInLineException extends Hive.IllegalMove {

        public GrassHopperNotMovingInLineException(String message) {
            super(message);
        }
    }
}
