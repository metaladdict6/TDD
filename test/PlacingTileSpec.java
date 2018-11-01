import nl.hanze.hive.BoardBuilder;
import nl.hanze.hive.Cell;
import nl.hanze.hive.Game;
import nl.hanze.hive.Hive;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by rrczi on 11-10-2018.
 * This class tests if illegal placements can occure in the game.
 */
public class PlacingTileSpec {

    @Test(expected =  Hive.IllegalMove.class)
    public void placePieceOnExistingPiece() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.play(Hive.Tile.BEETLE, 0, -3);
    }

    @Test(expected =  Hive.IllegalMove.class)
    public void placeWithoutNeighbours() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.play(Hive.Tile.BEETLE, 0, 0);
    }

    @Test(expected =  Hive.IllegalMove.class)
    public void placeNextToOpponent() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        game.play(Hive.Tile.SPIDER, 0, -3);
        game.play(Game.Tile.SOLDIER_ANT, -1, -3);
    }

    @Test(expected =  Hive.IllegalMove.class)
    public void placeTooManyPieces() throws Hive.IllegalMove{
        Game game = new Game();
        LinkedList<Hive.Tile> tiles = new LinkedList<>();
        BoardBuilder.initTiles(tiles);
        tiles.removeFirstOccurrence(Hive.Tile.BEETLE);
        tiles.removeFirstOccurrence(Hive.Tile.BEETLE);
        // The game only has twoo beetle, so this should throw an exeception.
        game.setWhiteNotPlayedTiles(tiles);
        game.play(Hive.Tile.BEETLE, 0, 0);
    }
}
