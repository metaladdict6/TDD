package nl.hanze.hive;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robert on 3-11-18.
 */
public class PlayHandler {


    private Game game;

    private MoveHandler moveHandler;

    public PlayHandler(Game game, MoveHandler moveHandler){
        this.game = game;
        this.moveHandler = moveHandler;
    }

    public void playTile(Hive.Tile tile, int q, int r) throws Hive.IllegalMove {
        if (isPieceAvailable(tile)) {
            HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
            Cell cell = game.getCell(grid, r, q);
            if(cell.size() != 0) {
                throw new Hive.IllegalMove("The coordinates you are trying to play too is occupied");
            }else{
                ArrayList<Cell> neighbours = this.game.findNeighbours(q, r);
                if(hasNotPlayedTile()) {
                    playTile(cell, tile);
                }else if(allFriendsNoEnemies(neighbours)){
                    playTile(cell, tile);
                }else {
                    throw new Hive.IllegalMove("The coordinate you are trying to play too is not adjunct to a friendly piece or " +
                            "is next to an opponents piece");
                }
            }
        }else {
            throw new Hive.IllegalMove("This piece isn't available");
        }
    }

    /**
     * This method checks if the current player has played any tiles.
     * @return If the current player has played a tile.
     */
    private boolean hasNotPlayedTile() {
        if(game.currentPlayer == Hive.Player.WHITE) {
            return game.getWhiteNotPlayedTiles().size() == 11;
        }else {
            return game.getBlackNotPlayedTiles().size() == 11;
        }
    }

    /**
     * This method plays the tile onto the board.
     * @param cell The cell the tile is played on.
     * @param tile The tile that is being played.
     */
    private void playTile(Cell cell, Hive.Tile tile){
        cell.add(game.currentPlayer, tile);
        if(tile == Hive.Tile.QUEEN_BEE) {
            this.moveHandler.updateQueen(cell);
        }
        updatePieces(tile);
    }

    /**
     * This method checks if the player isn't trying to play tile he doesn't have.
     * @param tile The tile the player wants to play.
     * @return
     */
    private boolean isPieceAvailable(Hive.Tile tile) {
        if (game.currentPlayer == Hive.Player.WHITE) {
            return game.getWhiteNotPlayedTiles().contains(tile);
        }else {
            return game.getBlackNotPlayedTiles().contains(tile);
        }
    }

    /**
     * This method removes one of the played tiles.
     * @param tile The tile that needs to be removed from one of the players lists.
     */
    private void updatePieces(Hive.Tile tile) {
        if (game.currentPlayer == Hive.Player.WHITE) {
            game.getWhiteNotPlayedTiles().removeFirstOccurrence(tile);
        }else {
            game.getBlackNotPlayedTiles().removeFirstOccurrence(tile);
        }
    }

    /**
     * This method checks if all the adjecten cells contain at least one friendly cell and no opposing pieces
     * @param neighbours A list of neighbours of the current coordinate the player wants to place an cell.
     * @return An indication if it's legal to place a piece on the current coordinate.
     */
    private boolean allFriendsNoEnemies(ArrayList<Cell> neighbours) {
        Boolean safe = false;
        for(Cell cell : neighbours){
            Hive.Player cellOwner = cell.cellOwner();
            if (cellOwner != null) {
                if (cellOwner == game.getOpponent()){
                    return false;
                }else if (cellOwner == game.currentPlayer) {
                    safe = true;
                }
            }
        }
        return safe;
    }

}
