package nl.hanze.hive;

/**
 * @author Robert Ziengs, Leon Wetzel
 */
public class SoldierAntMoveException extends Hive.IllegalMove {
    public SoldierAntMoveException() {
        super();
    }

    public SoldierAntMoveException(String message) {
        super(message);
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class SoldierAntMoveToOccupiedSpace extends Hive.IllegalMove {

        public SoldierAntMoveToOccupiedSpace(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class SoldierAntMoveToSameSpaceException extends Hive.IllegalMove {

        public SoldierAntMoveToSameSpaceException(String message) {
            super(message);
        }
    }

    /**
     * @author Robert Ziengs, Leon Wetzel
     */
    public static class SoldierAntNoPathToDesinationException extends Hive.IllegalMove {

        public SoldierAntNoPathToDesinationException(String message) {
            super(message);
        }
    }
}
