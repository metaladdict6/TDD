import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests if the board properly handles different situation's that can occur in the game.
 */
public class HiveSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSpaceWithoutNeighbours() {

    }


    @Test(expected =  Hive.IllegalMove.class)
    public void moveToTwoLevelsInOneMove() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, -2, 0);
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
