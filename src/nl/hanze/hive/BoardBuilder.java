package nl.hanze.hive;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class builds a board, initializes the tiles for a player and copies the board if required.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class BoardBuilder {
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
    public static HashMap <Integer, HashMap<Integer, Cell>> initiateGrid() {
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
     * This method copies the whole grid so that we can test for a situation without changing the game state.
     * @param grid A complete grid of a game;
     * @return A full copy of the given game grid.
     */
    public static HashMap<Integer, HashMap<Integer, Cell>> copyGrid(HashMap<Integer, HashMap<Integer, Cell>> grid) {
        HashMap<Integer, HashMap<Integer, Cell>> gridCopy = new HashMap<>();
        for(Integer key: grid.keySet()) {
            HashMap<Integer, Cell> rowCopy = new HashMap<>();
            gridCopy.put(key, rowCopy);
            HashMap<Integer, Cell> row = grid.get(key);
            for(Integer rowKey: row.keySet()) {
                try {
                    Cell orginal =  row.get(rowKey);
                    Cell copy = orginal.clone();
                    rowCopy.put(rowKey, copy);
                } catch (CloneNotSupportedException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
        return gridCopy;
    }

    /**
     * This method inserts all the required tiles a player should have.
     * @param tiles The list of tiles a player has.
     */
    public static void initialiseTiles(LinkedList<Hive.Tile> tiles) {
        tiles.add(Hive.Tile.QUEEN_BEE);
        addTiles(tiles, 2, Hive.Tile.SPIDER);
        addTiles(tiles, 2, Hive.Tile.BEETLE);
        addTiles(tiles, 3, Hive.Tile.SOLDIER_ANT);
        addTiles(tiles, 3, Hive.Tile.GRASSHOPPER);
    }

    /**
     * This method inserts a specific tile for a specific amount in the LinkedList
     * @param tiles The list of tiles where the tile is going to be inserted in.
     * @param amount The amount of tiles that needs to be inserted.
     * @param tile The tile that needs to be inserted
     */
    private static void addTiles(LinkedList<Hive.Tile> tiles, int amount, Hive.Tile tile) {
        for(int i = 0; i < amount; i++){
            tiles.add(tile);
        }
    }

}
