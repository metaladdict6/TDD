import nl.hanze.hive.Hive;
import org.junit.Test;

/**
 * Created by rrczi on 8-10-2018.
 */
public class QueenSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveTwoSquares() {

    }

    @Test
    public void moveOneSquare() {

    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToOccupiedSpace() {

    }
}
