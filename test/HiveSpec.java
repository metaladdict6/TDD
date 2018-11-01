import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rrczi on 8-10-2018.
 * This class tests if the board properly handles different situation's that can occur in the game.
 */
public class HiveSpec {

    @Test(expected = Hive.IllegalMove.class)
    public void moveToSpaceWithoutNeighbours() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(0).add(Game.Player.BLACK, Game.Tile.QUEEN_BEE);
        game.setBlackQueenCell(grid.get(0).get(0));
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        game.move(0, 0, 0, 1);
    }


    @Test(expected =  Hive.IllegalMove.class)
    public void moveToTwoLevelsInOneMove() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(-1));
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-3, 0, -2, 0);
    }

    @Test
    public void playerIsWinner() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(2).add(Game.Player.BLACK, Game.Tile.QUEEN_BEE);
        game.setBlackQueenCell(grid.get(0).get(2));
        grid.get(0).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE); // LEFT
        grid.get(0).get(3).add(Game.Player.WHITE, Game.Tile.BEETLE); // RIGHT
        grid.get(1).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE); // LEFT DOWN
        grid.get(1).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE); // RIGHT DOWN
        grid.get(-1).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE); // LEFT UP
        grid.get(-1).get(3).add(Game.Player.WHITE, Game.Tile.BEETLE); // RIGHT UP
        Assert.assertTrue(game.isWinner(Hive.Player.WHITE));
    }

    @Test
    public void playerCanPassTurn() {
        Game game = new Game();
        Assert.assertTrue(false);
    }

    @Test
    public void gameIsDraw() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        // Setting up the white win scenario
        grid.get(0).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(2).add(Game.Player.BLACK, Game.Tile.QUEEN_BEE);
        game.setBlackQueenCell(grid.get(0).get(2));
        grid.get(0).get(3).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-1).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-1).get(3).add(Game.Player.WHITE, Game.Tile.BEETLE);
        // Setting up the black win scenario
        grid.get(0).get(0).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell( grid.get(0).get(-1));
        grid.get(0).get(-2).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(1).get(-1).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(1).get(-2).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(-1).get(0).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(-1).get(-1).add(Game.Player.BLACK, Game.Tile.BEETLE);
        Assert.assertTrue(game.isDraw());
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveBeforeQueenPlacement() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-1, 0, 0, -1);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void breakTileChain() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(0).add(Game.Player.BLACK, Game.Tile.QUEEN_BEE);
        game.setBlackQueenCell(grid.get(0).get(0));
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        game.move(-1, 0, 0, 1);
    }


}
