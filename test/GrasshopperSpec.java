import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.GrassHopperMoveException;
import nl.hanze.hive.Hive;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests all the actions a Grasshopper specifically can do to trigger a illegal move.
 */
public class GrasshopperSpec {

    @Test(expected = GrassHopperMoveException.class)
    public void moveOneSquare() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-3));
        game.move(-3, 0, -2, -1);
    }

    @Test(expected = GrassHopperMoveException.class)
    public void moveToOccupiedSquare() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, -1, 0);
    }

    @Test(expected = GrassHopperMoveException.class)
    public void jumpOverUnOccupiedSpaces() throws Exception{
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        // grid.get(0).get(-1).add(Game.Tile.BEETLE); This field is empty so it needs to throw an Exception.
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, 1, 0);
    }

    @Test(expected = GrassHopperMoveException.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        game.move(-3, 0, -3, 0);
    }
}
