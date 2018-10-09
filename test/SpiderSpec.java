import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 */

public class SpiderSpec {

    @Test
    public void makesLegalMove() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.SPIDER);
        game.move(0, -3, 0, 0);
        Assert.assertEquals(Game.Tile.SPIDER, grid.get(0).get(0).pop());

    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Tile.SPIDER);
        game.move(0, -3, 0, -3);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveOverOtherPiece() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-2).add(Game.Tile.BEETLE);
        grid.get(0).get(-3).add(Game.Tile.SPIDER);
        game.move(0, -3, 0, 0);
    }

}
