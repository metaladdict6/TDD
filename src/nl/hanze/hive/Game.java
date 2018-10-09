package nl.hanze.hive;

import java.util.HashMap;

public class Game implements Hive {

    private int score;

    private HashMap<Integer, HashMap<Integer, Cell>> Grid;

    public Game() {
        System.out.println("Hello world");
        this.Grid = new HashMap<Integer, HashMap<Integer, Cell>>();
        this.buildGrid();
    }

    /**
     * This method builds the grid for the game.
     * The grid consists of two HashMaps.
     * The first HashMap has an Integer as key and represents the vertical level of the grid.
     * It holds another HashMap with the Horizontal level of the grid.
     * The second HashMap also an Integer as a key.
     * The second HashMap contains a Cell class.
     * This needs to be popped to remove the current top piece in the cell.
     */
    private void buildGrid() {
        HashMap<Integer, Cell> firstRow = new HashMap<Integer, Cell>();
        Integer rowNumber = -3;
        this.getGrid().put(0, firstRow);
        while (rowNumber < 4) {
            HashMap<Integer, Cell> row = new HashMap<Integer, Cell>();
            for (int i = 0; i < 4; i++) {
                row.put(i, new Cell(rowNumber, i));
            }
            this.getGrid().put(rowNumber, row);
            rowNumber++;
        }
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
        Cell fromCell = this.getGrid().get(fromQ).get(fromR);
        Piece piece = fromCell.pop();
        Cell toCell = this.getGrid().get(toQ).get(toR);
    }

    /**
     * This method returns a specifice instance of the Cell class.
     * @param q The vertical adress of the Cell we want.
     * @param r The horizontal adress of the cell we want.
     * @return
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HashMap<Integer, HashMap<Integer, Cell>> getGrid() {
        return Grid;
    }
}
