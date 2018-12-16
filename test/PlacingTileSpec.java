import nl.hanze.hive.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class tests if illegal placements can occure in the game.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class PlacingTileSpec {


    @Test
    public void playPieces() {
        Game game = new Game();
        try {
            game.play(Hive.Tile.BEETLE, 0, 0);
            game.play(Hive.Tile.SOLDIER_ANT, -3, -3);
            game.play(Hive.Tile.QUEEN_BEE, -1,0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        } finally {
            Assert.assertEquals(Hive.Tile.QUEEN_BEE, game.getCell(-1, 0).getTopTile());
        }
    }

    @Test(expected =  PlaceException.PlaceQueenBeforeContinuingException.class)
    public void needToPlaceQueenException() throws Exception {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0); // WHITE 1
        game.play(Hive.Tile.SOLDIER_ANT, -3, -3); // BLACK
        game.play(Hive.Tile.SOLDIER_ANT, -1,0); // WHITE 2
        game.play(Hive.Tile.BEETLE, -2, -3); // BLACK
        game.play(Hive.Tile.GRASSHOPPER, -2, 0); // WHITE 3
        game.play(Hive.Tile.BEETLE, -1, -3); // BLACK
        game.play(Hive.Tile.BEETLE, -1, 0); // WHITE
    }


    @Test(expected =  PlaceException.PlaceOnExistingPieceException.class)
    public void placePieceOnExistingPiece() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        grid.get(-2).get(-1).add(Game.Player.WHITE, Game.Tile.QUEEN_BEE);
        grid.get(-3).get(0).add(Game.Player.WHITE, Game.Tile.SPIDER);
        game.play(Hive.Tile.BEETLE, 0, -3);
    }

    @Test(expected =  PlaceException.PlaceWithoutNeighboursException.class)
    public void placeWithoutNeighbours() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.QUEEN_BEE, -3,-3);
        game.play(Hive.Tile.BEETLE, 3, 3);
    }

    @Test(expected =  PlaceException.PlaceNextToOpponentException.class)
    public void placeNextToOpponent() throws Exception {
        Game game = new Game();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        game.play(Hive.Tile.SPIDER, 0, -3);
        game.play(Game.Tile.SOLDIER_ANT, -1, -3);
    }

    @Test(expected =  PlaceException.PlaceTooManyPiecesException.class)
    public void placeTooManyPieces() throws Hive.IllegalMove{
        Game game = new Game();
        LinkedList<Hive.Tile> tiles = new LinkedList<>();
        BoardBuilder.initialiseTiles(tiles);
        tiles.removeFirstOccurrence(Hive.Tile.BEETLE);
        tiles.removeFirstOccurrence(Hive.Tile.BEETLE);
        // The game only has twoo beetle, so this should throw an exeception.
        game.setWhiteNotPlayedTiles(tiles);
        game.play(Hive.Tile.BEETLE, 0, 0);
    }
}
