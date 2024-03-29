package nl.hanze.hive;

import java.util.ArrayList;

/**
 * This class handles any and all concerning the playing of tiles.
 * The decision was made to keep the tiles in the game itself because it's part of the state of game.
 * This class could be expanded as to accept the game as a variable as well, but that isn't the current scope of
 * the project.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class PlayHandler {

    private Game game;

    private MoveHandler moveHandler;


    PlayHandler(Game game, MoveHandler moveHandler){
        this.game = game;
        this.moveHandler = moveHandler;
    }

    void playTile(Hive.Tile tile, int q, int r) throws Hive.IllegalMove {
        Cell cell = game.getCell(q, r);
        if (!isPieceAvailable(tile)){
            throw new PlaceException.PlaceTooManyPiecesException("This piece isn't available");
        }else if(!needToPlayQueen()){
            throw new PlaceException.PlaceQueenBeforeContinuingException("You have to play your Queen");
        } else if(cell.size() != 0) {
            throw new PlaceException.PlaceOnExistingPieceException("The coordinates you are trying to play too is occupied");
        } else {
            ArrayList<Cell> neighbours = this.game.findNeighbours(q, r);
            boolean allFriends = allFriends(neighbours);
            boolean noEnemies = noEnemies(neighbours);
            if (playerHasNotPlayedTile()) {
                if(!opponentHasPlayedTile()){
                    if(opponentNextToMe(neighbours)){
                        playTile(cell, tile);
                    }else {
                        throw new PlaceException.InitalPieceNeedsANeighbourException
                                ("The piece needs to be next to the first piece on the board");
                    }
                }
                else if (noEnemies){
                    playTile(cell, tile);
                } else {
                    throw new PlaceException.PlaceNextToOpponentException("The coordinate you are trying to play too is not "
                            + "adjunct to a friendly piece or is next to an opponents piece");
                }
            } else if(allFriends && noEnemies) {
                playTile(cell, tile);
            } else {
                if (!noEnemies) {
                    throw new PlaceException.PlaceNextToOpponentException("The coordinates you are trying to play too is" +
                            "next to an opponent");
                }
                else if (!allFriends) {
                    throw new PlaceException.PlaceWithoutNeighboursException("The coordinate you are trying to play too " +
                            "is not adjunct to a friendly piece");
                }else {
                    throw new Hive.IllegalMove("Unknown error");
                }

            }
        }

    }

    private boolean needToPlayQueen() {
        if(game.currentPlayer == Hive.Player.WHITE) {
            if (game.getWhiteNotPlayedTiles().size() == 8) {
                return game.queenPlayed();
            }else {
                return true;
            }
        } else {
            if (game.getBlackNotPlayedTiles().size() == 8) {
                return game.queenPlayed();
            }else {
                return true;
            }
        }
    }

    /**
     * This method checks if the current player has played any tiles.
     * @return If the current player has played a tile.
     */
    private boolean playerHasNotPlayedTile() {
        if (game.currentPlayer == Hive.Player.WHITE) {
            return game.getWhiteNotPlayedTiles().size() == 11;
        } else {
            return game.getBlackNotPlayedTiles().size() == 11;
        }
    }

    private boolean opponentHasPlayedTile() {
        if (game.getOpponent() == Hive.Player.WHITE) {
            return game.getWhiteNotPlayedTiles().size() == 11;
        } else {
            return game.getBlackNotPlayedTiles().size() == 11;
        }
    }

    /**
     * This method plays the tile onto the board.
     * @param cell The cell the tile is played on.
     * @param tile The tile that is being played.
     */
    private void playTile(Cell cell, Hive.Tile tile) {
        cell.add(game.currentPlayer, tile);
        if (tile == Hive.Tile.QUEEN_BEE) {
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
        } else {
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
        } else {
            game.getBlackNotPlayedTiles().removeFirstOccurrence(tile);
        }
    }

    /**
     * This method checks if all the adjecten cells contain at least one friendly cell and no opposing pieces
     * @param neighbours A list of neighbours of the current coordinate the player wants to place an cell.
     * @return An indication if it's legal to place a piece on the current coordinate.
     */
    private boolean allFriends(ArrayList<Cell> neighbours) throws Hive.IllegalMove {
        boolean isSafe = false;
        for(Cell cell : neighbours) {
            Hive.Player cellOwner = cell.cellOwner();
            if (cell.getTopTile() != null) {
                if (cell.cellOwner() == game.currentPlayer) {
                    isSafe = true;
                }
            }
        }
        return isSafe;
    }

    /**
     * This method checks if there any enemies adjectend to the chosen cell.
     * @param neighbours the neighbours of the chosen field.
     * @return
     * @throws Hive.IllegalMove
     */
    private boolean noEnemies(ArrayList<Cell> neighbours)  {
        for(Cell cell : neighbours) {
            Hive.Player cellOwner = cell.cellOwner();
            if (cellOwner != null) {
                if (cellOwner == game.getOpponent()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean opponentNextToMe(ArrayList<Cell> neighbours) {
        for(Cell cell : neighbours) {
            Hive.Player cellOwner = cell.cellOwner();
            if (cellOwner != null) {
                if (cellOwner == game.getOpponent()) {
                    return true;
                }
            }
        }
        return false;
    }
}
