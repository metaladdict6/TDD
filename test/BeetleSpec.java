import nl.hanze.hive.BeetleMoveException;
import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests if the game properly handles illegal moves of the Beetle.
 */
public class BeetleSpec {

    @Test(expected = BeetleMoveException.class)
    public void moveTwoSquares() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        game.move(0, 0, -2, 0);
    }

}
