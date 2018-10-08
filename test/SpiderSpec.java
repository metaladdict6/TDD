import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

/**
 * Created by rrczi on 8-10-2018.
 */

public class SpiderSpec {

    @Test
    public void makesLegalMove() throws Exception {
        Game game = new Game();
        game.move(0, 0, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        game.move(0, 0, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveOverOtherPiece() throws Exception {
        Game game = new Game();
        game.move(0, 0, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToAlreadyVisitedField() throws Exception {
        Game game = new Game();
        game.move(0, 0, 0, 0);
    }

}
