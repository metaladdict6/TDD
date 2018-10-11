import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests if the game properly handles illegal moves of the Beetle.
 */
public class BeetleSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveTwoSquares() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(0, -3, 0, -1);
    }

}
