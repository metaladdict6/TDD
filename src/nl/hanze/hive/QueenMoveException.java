package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class QueenMoveException extends Hive.IllegalMove {

    public QueenMoveException() {
        super();
    }

    public QueenMoveException(String message) {
        super(message);
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class QueenMoveToOccupiedSpaceException extends Hive.IllegalMove {

        public QueenMoveToOccupiedSpaceException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class QueenMoveTooFarException extends Hive.IllegalMove {

        public QueenMoveTooFarException(String message) {
            super(message);
        }
    }
}
