import nl.hanze.hive.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * This class tests all the actions a Grasshopper specifically can do to trigger a illegal move.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class GrasshopperSpec {

    @Test
    public void legalGrassHopperMove() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        try {
            game.move(-3, 0, 0, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {
            Assert.assertEquals(Game.Tile.GRASSHOPPER, grid.get(0).get(0).getTopTile());
        }
    }

    @Test(expected = GrassHopperMoveException.GrassHopperMoveOnSquareException.class)
    public void moveOneSquare() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        game.move(-3, 0, -2, -1);
    }

    @Test(expected = GrassHopperMoveException.GrassHopperMoveToOccupiedSquare.class)
    public void moveToOccupiedSquare() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, -1, 0);
    }

    @Test(expected = GrassHopperMoveException.GrassHopperMoveOverUnOccupiedException.class)
    public void jumpOverUnOccupiedSpaces() throws Exception{
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        // grid.get(0).get(-1).add(Game.Tile.BEETLE); This field is empty so it needs to throw an Exception.
        grid.get(0).get(1).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, 0, 0);
    }

    @Test(expected = GrassHopperMoveException.GrassHopperMoveToSamePlaceException.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        game.move(-3, 0, -3, 0);
    }
}
