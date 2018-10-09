import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 */
public class GrasshopperSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveOneSquare() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.GRASSHOPPER);
        game.move(0, -3, 0, -2);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToOccupiedSquare() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Tile.BEETLE);
        game.move(0, -3, 0, -2);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void jumpOverUnOccupiedSpaces() throws Exception{
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.GRASSHOPPER);
        grid.get(0).get(-2).add(Game.Tile.BEETLE);
        grid.get(0).get(-1).add(Game.Tile.BEETLE);
        grid.get(0).get(0).add(Game.Tile.BEETLE);
        game.move(0, -3, 0, 1);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.GRASSHOPPER);
        game.move(0, -3, 0, -3);
    }
}
