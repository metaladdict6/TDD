import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 */
public class SoldierAntSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveToOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.SOLDIER_ANT);
        grid.get(0).get(-2).add(Game.Tile.SPIDER);
        game.move(0, -3, 0, -2);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveOverOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.SOLDIER_ANT);
        grid.get(0).get(-2).add(Game.Tile.BEETLE);
        grid.get(0).get(-1).add(Game.Tile.BEETLE);
        game.move(0, -3, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.SOLDIER_ANT);
        game.move(0, -3, 0, -3);

    }
}
