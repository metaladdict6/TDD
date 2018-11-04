package nl.hanze.hive;

import java.util.Stack;

/**
 * This class will contain any and all tiles.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
public class Cell implements Cloneable {

    private int coordinateR;

    private int coordinateQ;

    private Stack<Hive.Player> tileOwnership = new Stack<>();

    private Stack<Hive.Tile> tiles = new Stack<>();

    /**
     * A Cell contains information about the player who possesses the files
     * and the amount of tiles.
     * @param coordinateR
     * @param coordinateQ
     */
    public Cell(int coordinateR, int coordinateQ) {
        setCoordinateR(coordinateR);
        setCoordinateQ(coordinateQ);
        this.tileOwnership = new Stack<>();
        this.tiles = new Stack<>();
    }

    public Cell(int coordinateR, int coordinateQ, Stack<Hive.Player> tileOwnership,  Stack<Hive.Tile> tiles){

    }

    /**
     * Adds a player to a tile.
     * @param player
     * @param tile
     */
    public void add(Hive.Player player, Hive.Tile tile) {
        tileOwnership.add(player);
        tiles.add(tile);
    }

    /**
     * Removes a player and tile and returns a tile.
     * @return
     */
    public Hive.Tile pop() {
        this.tileOwnership.pop();
        return this.tiles.pop();
    }

    /**
     * Get the top tile, if present.
     * @return
     */
    public Hive.Tile getTopTile() {
        if (tiles.size() == 0) {
            return null;
        }
        return this.tiles.get(tiles.size() - 1);
    }

    /**
     * Get the amount of tiles.
     * @return
     */
    public int size(){
        return tiles.size();
    }

    /**
     * Get the owner of the cell.
     * @return
     */
    public Hive.Player cellOwner() {
        if(tileOwnership.size() == 0){
            return null;
        }
        return tileOwnership.get(tileOwnership.size() - 1);
    }

    /**
     * Get the R coordinate from the cell.
     * @return
     */
    public int getCoordinateR() {
        return coordinateR;
    }

    /**
     * Set the new R coordinate of the cell.
     * @param coordinateR
     */
    public void setCoordinateR(int coordinateR) {
        this.coordinateR = coordinateR;
    }

    /**
     * Get the Q coordinate from the cell.
     * @return Q coordinate
     */
    public int getCoordinateQ() {
        return coordinateQ;
    }

    /**
     * Set the new Q coordinate of the cell.
     * @param coordinateQ
     */
    public void setCoordinateQ(int coordinateQ) {
        this.coordinateQ = coordinateQ;
    }

    /**
     * Clones the current Cell object.
     * @return
     * @throws CloneNotSupportedException
     */
    public Cell clone() throws CloneNotSupportedException {
        return (Cell) super.clone();
    }
}
