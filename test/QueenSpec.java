import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import nl.hanze.hive.QueenMoveException;
import org.junit.Test;

import java.util.HashMap;

/**
 * This class tests all the actions a Queen specifically can do to trigger a illegal move.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class QueenSpec {

    @Test(expected = QueenMoveException.class)
    public void moveTwoSquares() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        game.move(-3, 0, 0, -1);
    }

    @Test(expected = QueenMoveException.class)
    public void moveDiagonally() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        game.move(-3, 0, -1, -2);
    }


    @Test(expected = QueenMoveException.class)
    public void moveToOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-3).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-3).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-3).get(0));
        game.move(0, -3, 1, -3);
    }
}
