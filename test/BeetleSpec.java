import nl.hanze.hive.BeetleMoveException;
import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * This class tests if the game properly handles illegal moves of the Beetle.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class BeetleSpec {


    @Test
    public void legalBeetleMove() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        try {
            game.move(-1, 0, -2, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(Game.Tile.BEETLE, grid.get(0).get(-2).getTopTile());
        }
    }

    @Test
    public void moveToOccupiedSpace() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-2).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        try {
            game.move(-1, 0, -2, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(Game.Player.WHITE, grid.get(0).get(-2).cellOwner());
        }
    }

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
