import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import nl.hanze.hive.SoldierAntMoveException;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests all the actions a Soldier Ant specifically can do to trigger a illegal move.
 */
public class SoldierAntSpec {

    @Test(expected = SoldierAntMoveException.class)
    public void moveToOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.move(0, -3, 0, -2);
    }

    @Test(expected = SoldierAntMoveException.class)
    public void moveOverOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(0, -3, 0, 0);
    }

    @Test(expected = SoldierAntMoveException.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        game.move(0, -3, 0, -3);

    }
}
