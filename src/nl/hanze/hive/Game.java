package nl.hanze.hive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Game implements Hive {

    private HashMap<Integer, HashMap<Integer, Cell>> Grid;

    private LinkedList<Tile> blackNotPlayedTiles;

    private LinkedList<Tile> whiteNotPlayedTiles;

    public Hive.Player currentPlayer;

    private Cell blackQueenCell;

    private Cell whiteQueenCell;

    public MoveHandler moveHandler;

    private PlayHandler playHandler;

    public Game() {
        this.moveHandler = new MoveHandler(this);
        this.playHandler = new PlayHandler(this, moveHandler);
        this.Grid = BoardBuilder.InitiateGrid();
        this.currentPlayer = Player.WHITE;
        this.blackNotPlayedTiles = new LinkedList<>();
        this.whiteNotPlayedTiles = new LinkedList<>();
        BoardBuilder.initTiles(this.blackNotPlayedTiles);
        BoardBuilder.initTiles(this.whiteNotPlayedTiles);
    }

    /**
     * This method contains all the logic for making sure the tile is placed without breaking any rules.
     * @param tile Tile to play
     * @param q    Q coordinate of hexagon to play to
     * @param r    R coordinate of hexagon to play to
     * @throws IllegalMove If the tile could not be played
     */
    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        this.playHandler.playTile(tile, q, r);
        nextPlayer();

    }

    /**
     * Move an existing tile.
     * @param fromQ Q coordinate of the tile to move
     * @param fromR R coordinate of the tile to move
     * @param toQ   Q coordinate of the hexagon to move to
     * @param toR   R coordinare of the hexagon to move to
     * @throws IllegalMove If the tile could not be moved
     */
    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        moveHandler.moveTile(fromQ, fromR, toQ, toR);
        nextPlayer();
    }

    /**
     * This method checks if the current player may move his pieces.
     * @return A boolean thats says false or true.
     */
    public boolean queenPlayed(){
        if (currentPlayer == Player.BLACK) {
            if (blackQueenCell == null) {
                return false;
            }
        }else {
            if (whiteQueenCell == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pass the turn.
     * @throws IllegalMove If the turn could not be passed
     */
    @Override
    public void pass() throws IllegalMove {
        String message = "You have a valid move";
        if(!hasValidMovesOrCanPlayTile(message)) {
            nextPlayer();
        }else {
            System.out.println(message);
        }
    }

    /**
     * This method checks if the player can actually
     * @param message The message that will be edited if the method finds a move or play.
     * @return If the current player can actually can do a move and or play a tile.
     */
    private boolean hasValidMovesOrCanPlayTile(String message){
        Game game = new Game();
        game.setGrid(BoardBuilder.copyGrid(this.Grid));
        game.setWhiteNotPlayedTiles(new LinkedList<>(this.whiteNotPlayedTiles));
        game.setBlackNotPlayedTiles(new LinkedList<>(this.blackNotPlayedTiles));
        game.currentPlayer = currentPlayer;
        for(Integer key: game.getGrid().keySet()) {
            HashMap<Integer, Cell> row = game.getGrid().get(key);
            for(Integer rowKey: row.keySet()){
                Cell currentCell = game.getCell(rowKey, key);
                Cell destinationCell = game.getCell(rowKey, key);
                try{
                    Cell cell = game.moveHandler.genericRulesChecker(currentCell.getCoordinate_Q(),
                            currentCell.getCoordinate_R(), destinationCell.getCoordinate_Q(), destinationCell.getCoordinate_Q());

                    game.moveHandler.checkTileSpecificRules(currentCell.getCoordinate_Q(),
                            currentCell.getCoordinate_R(), destinationCell.getCoordinate_Q(), destinationCell.getCoordinate_Q(),
                            cell);

                    String destinationCoordinates = "q = " + destinationCell.getCoordinate_Q() + " r =" + destinationCell.getCoordinate_R();
                    String startingCoordinates = "q = " + currentCell.getCoordinate_Q() + " r = " + currentCell.getCoordinate_R();
                    message = "There is a valid coordinate from " + startingCoordinates + " to " + destinationCoordinates;
                    return true;
                }catch (IllegalMove illegalMove){
                    System.out.println(illegalMove.getMessage());
                }catch (Exception exception){
                    System.out.println("Something unexpected happened");
                    System.out.println(exception.getMessage());
                }
            }
        }
        return true;
    }

    /**
     * Check whether the given player is the winner.
     * @param player Player to check
     * @return Boolean
     */
    @Override
    public boolean isWinner(Hive.Player player) {
        Cell queenCell = null;
        if(player == Player.WHITE) {
            queenCell = blackQueenCell;
        }else {
            queenCell = whiteQueenCell;
        }
        if (queenCell == null) {
            return false;
        }
        ArrayList<Cell> neighbours = findNeighbours(queenCell.getCoordinate_Q(), queenCell.getCoordinate_R());
        for(Cell neighbour: neighbours) {
            Player cellOwner = neighbour.cellOwner();
            if (player != cellOwner) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the game is a draw.
     *
     * @return Boolean
     */
    @Override
    public boolean isDraw() {
        return isWinner(Player.WHITE) && isWinner(Player.BLACK);
    }

    /**
     * This method changes the current player making a move or placing a tile.
     */
    private void nextPlayer() {
        if (currentPlayer == Player.WHITE) {
            currentPlayer = Player.BLACK;
        }else {
            currentPlayer = Player.WHITE;
        }
    }

    /**
     * This method returns all the neighbours of a specific coordinate. This method manually changes the cells
     * that are added to the returning list.
     * @param q The q coordinate. This indicates the horizontal position inside the grid.
     * @param r The r coordinate. This indicates the vertical position inside the grid.
     * @return Returns a list of cells that indicates the neighbours of the coordinate r and q.
     */
    public ArrayList<Cell> findNeighbours(int q, int r){
        HashMap<Integer, HashMap<Integer, Cell>> grid = this.getGrid();
        return this.findNeighbours(q, r, grid);
    }

    /**
     * This method is used by the move handler to check if any move breaks the tile chain.
     * @param q The horizontal coordinate that is being checked for neighbours at this point
     * @param r The vertical coordinate that is being checked for neighbours at this point
     * @param grid The grid a tile has been moved and to check if there's any rule breaking.
     * @return The neighbours based the q and r variable.
     */
    public ArrayList<Cell> findNeighbours(int q, int r,  HashMap<Integer, HashMap<Integer, Cell>> grid ) {
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(getCell(grid, r, q - 1));        // LEFT CELL
        cells.add(getCell(grid, r, q + 1));        // RIGHT CELL
        cells.add(getCell(grid, r - 1, q));        // LEFT UP CELL
        cells.add(getCell(grid, r - 1, q + 1)); // RIGHT UP CELL
        cells.add(getCell(grid, r + 1, q - 1)); // LEFT DOWN CELL;
        cells.add(getCell(grid, r + 1, q));        // RIGHT DOWN CELL
        return cells;
    }

    /**
     * This method returns a specific instance of the Cell class.
     * @param q The vertical address of the Cell we want.
     * @param r The horizontal address of the cell we want.
     * @return The cell in accordance with q and r.
     */
    public Cell getCell(int q, int r) {
        return getCell(this.getGrid(), r, q);
    }

        /**
         * This method either returns the cell a player wants or creates a new cell and then returns it.
         * @param grid The current grid of the game.
         * @param r The r coordinate. This indicates the vertical position inside the grid.
         * @param q The q coordinate. This indicates the horizontal position inside the grid.
         * @return
         */
        Cell getCell(HashMap<Integer, HashMap<Integer, Cell>> grid, int r, int q){
        if (grid.containsKey(r)){
            HashMap<Integer, Cell> row = grid.get(r);
            if (row.containsKey(q)) {
                return row.get(q);
            }else {
                Cell cell = new Cell(r, q);
                row.put(q, cell);
                return cell;
            }
        }else {
            HashMap<Integer, Cell> row = new HashMap<Integer, Cell>();
            grid.put(r, row);
            Cell cell = new Cell(r, q);
            row.put(q, cell);
            return cell;
        }
    }

    /**
     * This method returns the opponent of the  current player.
     * @return The oppossing Player
     */
    Player getOpponent(){
        if (currentPlayer == Player.BLACK){
            return Player.WHITE;
        }else {
            return Player.BLACK;
        }
    }


    public HashMap<Integer, HashMap<Integer, Cell>> getGrid() {
        return Grid;
    }

    public void setBlackQueenCell(Cell blackQueenCell) {
        this.blackQueenCell = blackQueenCell;
    }

    public void setWhiteQueenCell(Cell whiteQueenCell) {
        this.whiteQueenCell = whiteQueenCell;
    }

    public void setBlackNotPlayedTiles(LinkedList<Tile> blackNotPlayedTiles) {
        this.blackNotPlayedTiles = blackNotPlayedTiles;
    }

    public void setWhiteNotPlayedTiles(LinkedList<Tile> whiteNotPlayedTiles) {
        this.whiteNotPlayedTiles = whiteNotPlayedTiles;
    }

    public LinkedList<Tile> getBlackNotPlayedTiles() {
        return blackNotPlayedTiles;
    }

    public LinkedList<Tile> getWhiteNotPlayedTiles() {
        return whiteNotPlayedTiles;
    }

    public void setGrid(HashMap<Integer, HashMap<Integer, Cell>> grid) {
        this.Grid = grid;
    }
}
