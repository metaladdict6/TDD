import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

/**
 * Created by rrczi on 8-10-2018.
 */
public class HiveSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSpaceWithoutNeighbours() {

    }

    @Test
    public void playerIsWinner() {

    }

    @Test
    public void playerCanPassTurn() {

    }

    @Test
    public void gameIsDraw() {

    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveBeforeQueenPlacement() throws Exception {
        Game game = new Game();
        game.move(0, 0, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void breakTileChain() {

    }
}
