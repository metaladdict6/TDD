import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests all the actions a spider specifically can do to trigger a illegal move.
 * It also checks if the board handles a legal move properly.
 */

public class SpiderSpec {

    @Test
    public void makesLegalMove() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(-1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-2).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.move(-3, 0, 0, -3);
        Assert.assertEquals(Game.Tile.SPIDER, grid.get(-3).get(0).pop());

    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(-1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-2).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.move(-3, 0, -3, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveOverOtherPiece() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(-1).get(-2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.move(-3, 0, 0, -3);
        Assert.assertEquals(Game.Tile.SPIDER, grid.get(-3).get(0).pop());
    }



}
