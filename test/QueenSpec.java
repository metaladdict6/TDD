import nl.hanze.hive.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * This class tests all the actions a Queen specifically can do to trigger a illegal move.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class QueenSpec {

    @Test
    public void makeLegalQueenMove() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        try {
            game.move(-3, 0, -2, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(Game.Tile.QUEEN_BEE, grid.get(0).get(-2).getTopTile());
        }
    }

    @Test
    public void removedFromOldLocation(){
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        try {
            game.move(-3, 0, -2, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(null, grid.get(0).get(-3).getTopTile());
        }
    }

    @Test
    public void updatesQueenCell() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        try {
            game.move(-3, 0, -2, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Cell expectedQueenCell = grid.get(0).get(-2);
            Cell queenActuallQueenCell = game.getWhiteQueenCell();
            Assert.assertEquals(expectedQueenCell, queenActuallQueenCell);
        }
    }

    @Test(expected = QueenMoveException.QueenMoveTooFarException.class)
    public void moveTwoSquares() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        game.move(-3, 0, 0, -1);
    }

    @Test(expected = QueenMoveException.QueenMoveTooFarException.class)
    public void moveDiagonally() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        game.move(-3, 0, -1, -2);
    }


    @Test(expected = QueenMoveException.QueenMoveToOccupiedSpaceException.class)
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
