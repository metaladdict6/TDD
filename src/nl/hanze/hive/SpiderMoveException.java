package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class SpiderMoveException extends Hive.IllegalMove {
    public SpiderMoveException(String message) {
        super(message);
    }

    /**
     * Created by Robert Ziengs on 16-12-2018.
     */
    public static class SpiderMoveNotFarEnoughException extends Hive.IllegalMove {

        public SpiderMoveNotFarEnoughException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class SpiderMoveToOccupiedSpaceException extends Hive.IllegalMove {

        public SpiderMoveToOccupiedSpaceException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class SpiderMoveTooFarAwayException extends Hive.IllegalMove {

        public SpiderMoveTooFarAwayException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class SpiderMoveToSameSpaceException extends Hive.IllegalMove {

        public SpiderMoveToSameSpaceException(String message){
            super(message);
        }
    }
}
