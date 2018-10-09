package nl.hanze.hive;

import java.util.HashMap;

public class Game implements Hive {

    private HashMap<Integer, HashMap<Integer, Cell>> Grid;

    public Game() {
        this.Grid = InitiateGrid();
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
        Integer rowNumber = -3;
        grid.put(rowNumber, buildGridRow(rowNumber, 0, 3));
        rowNumber++; // - 2
        grid.put(rowNumber, buildGridRow(rowNumber, -1, 3));
        rowNumber++;  // - 1
        grid.put(rowNumber, buildGridRow(rowNumber, -2, 3));
        rowNumber++; // 0
        grid.put(rowNumber, buildGridRow(rowNumber, -3, 3));
        rowNumber++; // + 1
        grid.put(rowNumber, buildGridRow(rowNumber, -3, 2));
        rowNumber++; // + 2
        grid.put(rowNumber, buildGridRow(rowNumber, -3, 1));
        rowNumber++; // + 3
        grid.put(rowNumber, buildGridRow(rowNumber, -3, 0));
        return grid;
    }

    private static HashMap<Integer, Cell> buildGridRow(Integer rowNumber, Integer startPoint, Integer endPoint) {
        HashMap<Integer, Cell> row = new HashMap<Integer, Cell>();
        for (Integer i = startPoint; i < endPoint + 1; i++) {
            row.put(i, new Cell(rowNumber, i));
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
        pass();
    }

    /**
     * Move an existing tile.
     *
     * @param fromQ Q coordinate of the tile to move
     * @param fromR R coordinate of the tile to move
     * @param toQ   Q coordinate of the hexagon to move to
     * @param toR   R coordinare of the hexagon to move to
     * @throws IllegalMove If the tile could not be moved
     */
    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        Cell fromCell = getCell(fromQ, fromR);
        Piece piece = fromCell.pop();
        Cell toCell = getCell(toQ, toR);
    }

    /**
     * This method returns a specific instance of the Cell class.
     * @param q The vertical address of the Cell we want.
     * @param r The horizontal address of the cell we want.
     * @return The cell in accordance with q and r.
     */
    private Cell getCell(int q, int r) {
        return this.getGrid().get(q).get(r);
    }

    /**
     * Pass the turn.
     *
     * @throws IllegalMove If the turn could not be passed
     */
    @Override
    public void pass() throws IllegalMove {

    }

    /**
     * Check whether the given player is the winner.
     *
     * @param player Player to check
     * @return Boolean
     */
    @Override
    public boolean isWinner(Player player) {
        return false;
    }

    /**
     * Check whether the game is a draw.
     *
     * @return Boolean
     */
    @Override
    public boolean isDraw() {
        return false;
    }

    public HashMap<Integer, HashMap<Integer, Cell>> getGrid() {
        return Grid;
    }
}
