import nl.hanze.hive.*;
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
