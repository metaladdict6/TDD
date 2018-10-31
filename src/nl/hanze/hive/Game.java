package nl.hanze.hive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Game implements Hive {

    private HashMap<Integer, HashMap<Integer, Cell>> Grid;

    private LinkedList<Tile> blackPlayedTiles;

    private LinkedList<Tile> whitePlayedTiles;

    public Player currentPlayer;

    private Cell blackQueenCell;

    private Cell whiteQueenCell;

    private MoveHandler moveHandler;

    public Game() {
        this.moveHandler = new MoveHandler(this);
        this.Grid = InitiateGrid();
        this.currentPlayer = Player.WHITE;
        this.blackPlayedTiles = new LinkedList<>();
        this.whitePlayedTiles = new LinkedList<>();
        initTiles(this.blackPlayedTiles);
        initTiles(this.whitePlayedTiles);
    }

    /**
     * This method inserts all the required tiles a player should have.
     * @param tiles The list of tiles a player has.
     */
    private void initTiles(LinkedList<Tile> tiles) {
        tiles.add(Tile.QUEEN_BEE);
        addTiles(tiles, 2, Tile.SPIDER);
        addTiles(tiles, 2, Tile.BEETLE);
        addTiles(tiles, 3, Tile.SOLDIER_ANT);
        addTiles(tiles, 3, Tile.GRASSHOPPER);
    }

    /**
     * This method inserts a specifice tile for a specifice amount in the LinkedList
     * @param tiles The list of tiles where the tile is going to be inserted in.
     * @param amount The amount of tiles that needs to be inserted.
     * @param tile The tile that needs to be inserted
     */
    private void addTiles(LinkedList<Tile> tiles, int amount, Tile tile){
        for(int i = 0; i < amount; i++){
            tiles.add(tile);
        }
    }

    /**
     * This method builds the grid for the game.
     * The grid consists of two HashMaps.
     * The first HashMap has an Integer as key and represents the vertical level of the grid.
     * It holds another HashMap with the Horizontal level of the grid.
     * The second HashMap also an Integer as a key.
     * The second HashMap contains a Cell class.
     * This needs to be popped to remove the current top piece in the cell.
     * @return An empty grid
     */
    private static HashMap <Integer, HashMap<Integer, Cell>> InitiateGrid() {
        HashMap <Integer, HashMap<Integer, Cell>> grid = new HashMap<>();
        Integer r = -3;
        grid.put(r, buildGridRow(r, 0, 3));
        r++; // - 2
        grid.put(r, buildGridRow(r, -1, 3));
        r++;  // - 1
        grid.put(r, buildGridRow(r, -2, 3));
        r++; // 0
        grid.put(r, buildGridRow(r, -3, 3));
        r++; // + 1
        grid.put(r, buildGridRow(r, -3, 2));
        r++; // + 2
        grid.put(r, buildGridRow(r, -3, 1));
        r++; // + 3
        grid.put(r, buildGridRow(r, -3, 0));
        return grid;
    }

    /**
     * This method creates a row for the grid.
     * It does this through creating a loop for the x values in a row. The Y axis is incremented in a different
     * section.
     * @param r The row number is the vertical value of the grid. It could also be described as the Y axis.
     * @param startPoint The starting point for the loop.
     * @param endPoint The end point of the loop.
     * @return A HashMap with all the cells in a row.
     */
    private static HashMap<Integer, Cell> buildGridRow(Integer r, Integer startPoint, Integer endPoint) {
        HashMap<Integer, Cell> row = new HashMap<Integer, Cell>();
        for (Integer q = startPoint; q < endPoint + 1; q++) {
            row.put(q, new Cell(r, q));
        }
        return row;
    }

    /**
     * Play a new tile.
     * @param tile Tile to play
     * @param q    Q coordinate of hexagon to play to
     * @param r    R coordinate of hexagon to play to
     * @throws IllegalMove If the tile could not be played
     */
    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        HashMap<Integer, HashMap<Integer, Cell>> grid = getGrid();
        Cell cell = getCell(grid, r, q);
        if(cell.size() != 0) {
            throw new IllegalMove("The coordinates you are trying to play too is occupied");
        }else{
            ArrayList<Cell> neighbours = this.findNeighbours(q, r);
            if(allFriendsNoEnemies(neighbours)){
                cell.add(currentPlayer, tile);
                this.updateQueenCoordinate(tile, cell);
            }else {
                throw new IllegalMove("The coordinate you are trying to play too is not adject to a friendly piece or " +
                        "is next to an opponents piece");
            }
        }
        nextPlayer();
    }

    /**
     * This method sets the intial position of the Queen.
     * @param tile The tile that just has been placed.
     * @param cell The cell the tile has been placed in.
     */
    private void updateQueenCoordinate(Tile tile, Cell cell){
        if (tile == Tile.QUEEN_BEE){
            if (currentPlayer == Player.WHITE){
                whiteQueenCell = cell;
            }else {
                blackQueenCell = cell;
            }
        }
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
     * This method returns a specific instance of the Cell class.
     * @param q The vertical address of the Cell we want.
     * @param r The horizontal address of the cell we want.
     * @return The cell in accordance with q and r.
     */
    public Cell getCell(int q, int r) {
        return this.getGrid().get(r).get(q);
    }

    /**
     * Pass the turn.
     * @throws IllegalMove If the turn could not be passed
     */
    @Override
    public void pass() throws IllegalMove {

    }

    /**
     * Check whether the given player is the winner.
     * @param player Player to check
     * @return Boolean
     */
    @Override
    public boolean isWinner(Player player) {
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

    public HashMap<Integer, HashMap<Integer, Cell>> getGrid() {
        return Grid;
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
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(getCell(grid, r, q - 1));        // LEFT CELL
        cells.add(getCell(grid, r, q + 1));        // RIGHT CELL
        cells.add(getCell(grid, r - 1, q));        // LEFT UP CELL
        cells.add(getCell(grid, r - 1, q + 1)); // RIGHT UP CELL
        cells.add(getCell(grid, r + 1, q - 1)); // LEFT DOWN CELL;
        cells.add(getCell(grid, r + 1, q));        // RIGHT DOWN CELL

//        System.out.println("LEFT CELL = " + r + " and " + (q-1));
//        System.out.println(getCell(grid, r, q - 1).cellOwner());
//        System.out.println("RIGHT CELL = " + r + " and " + (q+1));
//        System.out.println(getCell(grid, r, q + 1).cellOwner());
//        System.out.println("LEFT UP CELL = " + (r - 1) + " and " + (q));
//        System.out.println(getCell(grid, r - 1, q).cellOwner());
//        System.out.println("RIGHT UP CELL = " + (r - 1) + " and " + (q + 1));
//        System.out.println(getCell(grid, r - 1, + 1).cellOwner());
//        System.out.println("LEFT DOWN CELL = " + (r + 1) + " and " + (q - 1));
//        System.out.println(getCell(grid, r + 1, q - 1).cellOwner());
//        System.out.println("RIGHT DOWN CELL = " + (r + 1) + " and " + (q));
//        System.out.println(getCell(grid, r + 1, q).cellOwner());
        return cells;
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
         * This method either returns the cell a player wants or creates a new cell and then returns it.
         * @param grid The current grid of the game.
         * @param r The r coordinate. This indicates the vertical position inside the grid.
         * @param q The q coordinate. This indicates the horizontal position inside the grid.
         * @return
         */
    private Cell getCell(HashMap<Integer, HashMap<Integer, Cell>> grid, int r, int q){
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
    private Player getOpponent(){
        if (currentPlayer == Player.BLACK){
            return Player.WHITE;
        }else {
            return Player.BLACK;
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
            Player cellOwner = cell.cellOwner();
            if (cellOwner != null) {
                if (cellOwner == getOpponent()){
                    return false;
                }else if (cellOwner == currentPlayer) {
                    safe = true;
                }
            }
        }
        return safe;
    }

    public void setBlackQueenCell(Cell blackQueenCell) {
        this.blackQueenCell = blackQueenCell;
    }

    public void setWhiteQueenCell(Cell whiteQueenCell) {
        this.whiteQueenCell = whiteQueenCell;
    }
}
