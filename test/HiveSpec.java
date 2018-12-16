import nl.hanze.hive.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class tests if the board properly handles different situation's that can occur in the game.
 * @author Robert Ziengs, Leon Wetzel
 */
public class HiveSpec {

    @Test
    public void nextPlayerIsSelected() {
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
            Assert.assertEquals(game.currentPlayer, Hive.Player.BLACK);
        }
    }

    @Test(expected = GameExceptions.GameMoveToSpaceWithoutNeighboursException.class)
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

    @Test
    public void moveBetweenLevels() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(-1));
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        try {
            game.move(-3, 0, -2, 0);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove.getMessage());
        } finally {

        }
    }

    @Test(expected =  GameExceptions.GameToDeepMoveException.class)
    public void moveToTwoLevelsInOneMove() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(-2).get(-1));
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SPIDER);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
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
        LinkedList<Hive.Tile> tiles = new LinkedList<>();
        BoardBuilder.initialiseTiles(game.getBlackNotPlayedTiles());
        BoardBuilder.initialiseTiles(game.getWhiteNotPlayedTiles());
        try {
            game.getCell(0, 0).add(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE);
            game.getBlackNotPlayedTiles().removeFirstOccurrence(Hive.Tile.QUEEN_BEE);
            game.setBlackQueenCell(game.getCell(0, 0));
            game.getCell(0, 1).add(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT);
            game.getCell(-1, 1).add(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT);
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SOLDIER_ANT);
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SOLDIER_ANT);
            game.getCell(0, 2).add(Hive.Player.WHITE, Hive.Tile.BEETLE);
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(Hive.Tile.BEETLE);
            game.getCell(1, 2).add(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT);
            game.getBlackNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SOLDIER_ANT);
            game.getCell(-2, 2).add(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT);
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SOLDIER_ANT);
            game.getCell(-2, 3).add(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT);
            game.getBlackNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SOLDIER_ANT);
            game.getCell(-2, 1).add(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE);
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(Hive.Tile.QUEEN_BEE);
            game.setWhiteQueenCell(game.getCell(-2, 1));
            game.getCell(-3, 1).add(Hive.Player.BLACK, Hive.Tile.SPIDER);
            game.getBlackNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SPIDER);
            game.getCell(-1, 0).add(Hive.Player.WHITE, Hive.Tile.SPIDER);
            game.getBlackNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SPIDER);
            game.getCell(-1, -1).add(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT);
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(Hive.Tile.BEETLE);
            game.getCell(-1, -2).add(Hive.Player.BLACK, Hive.Tile.SPIDER);
            game.getBlackNotPlayedTiles().removeFirstOccurrence(Hive.Tile.SPIDER);
            game.getCell(0, -1).add(Hive.Player.BLACK, Hive.Tile.BEETLE);
            game.pass();
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
        }finally {
            Assert.assertTrue(game.currentPlayer == Hive.Player.BLACK);
        }
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

    @Test(expected = GameExceptions.GameMoveBeforeQueenPlacement.class)
    public void moveBeforeQueenPlacement() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-3).add(Game.Player.BLACK, Game.Tile.BEETLE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(-1, 0, 0, -1);
    }

    @Test(expected = GameBreakTileChainException.class)
    public void breakTileChain() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(0).add(Game.Player.BLACK, Game.Tile.QUEEN_BEE);
        game.setBlackQueenCell(grid.get(0).get(0));
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        game.move(-1, 0, -3, 0);
    }


}
