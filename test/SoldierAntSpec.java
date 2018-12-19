import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import nl.hanze.hive.SoldierAntMoveException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * This class tests all the actions a Soldier Ant specifically can do to trigger a illegal move.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class SoldierAntSpec {

    @Test
    public void soldierAntLegalMove() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(2).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(2).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        try {
            game.move(0, 0, 3, 0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        } finally {
            Assert.assertEquals(Game.Tile.SOLDIER_ANT, grid.get(0).get(3).getTopTile());
        }
    }

    @Test
    public void soliderIsRemovedFromOldLocation() {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(2).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(2).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        try {
            game.move(0, 0, 3, 0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        } finally {
            Hive.Tile old = grid.get(0).get(0).getTopTile();
            Assert.assertEquals(null, old);
        }
    }

    @Test(expected = SoldierAntMoveException.SoldierAntNoPathToDesinationException.class)
    public void noPiecesConnectToDestination() {
        Game game = new Game();
        Hive.Player white  = Hive.Player.WHITE;
        Hive.Tile beetle = Hive.Tile.BEETLE;
        Hive.Tile soldier = Hive.Tile.SOLDIER_ANT;
        game.getCell(0, 3).add(white, soldier);
        game.getCell(-1, 3).add(white, beetle);
        game.getCell(-2, 3).add(white, beetle);
        game.getCell(-2, 2).add(white, beetle);
        game.getCell(-2, 1).add(white, beetle);
        game.getCell(-2, 0).add(white, beetle);
        game.getCell(-1, -1).add(white, beetle);
        game.getCell(2, -2).add(white, Hive.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(game.getCell(2, -2));
        try {
            game.move(0, 3, 2, -1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }
    }

    @Test(expected = SoldierAntMoveException.SoldierAntMoveToOccupiedSpace.class)
    public void moveToOccupiedSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(-2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(-3).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        game.move(-3, 0, -2, 0);
    }

    @Test(expected = SoldierAntMoveException.SoldierAntNoPathToDesinationException.class)
    public void cannotMoveThroughMaze() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-1).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(-1).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(0).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(1).get(1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(2).get(0).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(2).get(-1).add(Game.Player.WHITE, Game.Tile.BEETLE);
        grid.get(0).get(0).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(0).get(2).add(Game.Player.WHITE, Game.Tile.BEETLE);
        game.move(0, 0, 3, 0);
    }

    @Test(expected = SoldierAntMoveException.SoldierAntMoveToSameSpaceException.class)
    public void moveToSameSpace() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-3).get(1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        game.setWhiteQueenCell(grid.get(0).get(-2));
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SOLDIER_ANT);
        game.move(0, -3, 0, -3);

    }
}
