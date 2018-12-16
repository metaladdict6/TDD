import nl.hanze.hive.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * This class tests all the actions a spider specifically can do to trigger a illegal move.
 * It also checks if the board handles a legal move properly.
 *
 * @author Robert Ziengs, Leon Wetzel
 */

public class SpiderSpec {

    @Test
    public void makesLegalMove() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(-1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-1).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-2).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(0));
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        try{
            game.move(-3, 0, 0, -3);
        }catch (Exception exception) {
            System.out.print(exception.getMessage());
        }finally {
            Assert.assertEquals(Game.Tile.SPIDER, grid.get(-3).get(0).getTopTile());
        }
    }

    @Test(expected = SpiderMoveToOccupiedSpaceException.class)
    public void moveToOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(-1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-1).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);

        grid.get(-2).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(0));
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, 0, -3);

    }

    @Test(expected = SpiderMoveToSameSpaceException.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(0));
        grid.get(-2).get(1).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.move(1, -2, 1, -2);
    }

    @Test(expected = SpiderMoveTooFarAwayException.class)
    public void moveFourSpaces() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(-1).get(-2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(-1));
        game.move(-3, 0, 0, -3);
    }

    @Test(expected = SpiderMoveNotFarEnoughException.class)
    public void moveTwoSpacesOrLess() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        grid.get(-1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-1).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-2).get(0).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(0));
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.move(-3, 0, -1, -2);
    }
}
