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
        game.getCell(-1, 0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.getCell(-2, 0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.getCell(-3, 0).add(Hive.Player.WHITE, Hive.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        try {
            game.move(-1, 0, -1, -1);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(Game.Tile.BEETLE, grid.get(-1).get(-1).getTopTile());
        }
    }

    @Test
    public void beetleRemovedFromOldSpace(){
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        game.getCell(-1, 0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.getCell(-2, 0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.getCell(-3, 0).add(Hive.Player.WHITE, Hive.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        try {
            game.move(-1, 0, -1, -1);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(null, game.getCell(-1, 0).getTopTile());
        }
    }

    @Test
    public void moveToOccupiedSpace() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        game.getCell(-1, 0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.getCell(-2, 0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.getCell(-3, 0).add(Hive.Player.WHITE, Hive.Tile.SPIDER);
        game.getCell(-4, 0).add(Hive.Player.WHITE, Hive.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        try {
            game.move(-4, 0, -3, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(Game.Tile.BEETLE, grid.get(0).get(-3).getTopTile());
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
