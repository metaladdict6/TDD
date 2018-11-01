package nl.hanze.hive;

import java.util.Stack;

/**
 * Created by rrczi on 9-10-2018.
 * This
 */
public class Cell implements Cloneable{

    private int Coordinate_R;

    private int Coordinate_Q;

    private Stack<Hive.Player> tileOwnership = new Stack<>();

    private Stack<Hive.Tile> tiles = new Stack<>();

    public Cell(int coordinateR, int coordinateQ) {
        setCoordinate_R(coordinateR);
        setCoordinate_Q(coordinateQ);
        this.tileOwnership = new Stack<>();
        this.tiles = new Stack<>();
    }

    public Cell(int coordinateR, int coordinateQ, Stack<Hive.Player> tileOwnership,  Stack<Hive.Tile> tiles){

    }

    public void add(Hive.Player player, Hive.Tile tile) {
        tileOwnership.add(player);
        tiles.add(tile);
    }

    public Hive.Tile pop() {
        this.tileOwnership.pop();
        return this.tiles.pop();
    }

    public Hive.Tile getTopTile() {
        if (tiles.size() == 0) {
            return null;
        }
        return this.tiles.get(tiles.size() - 1);
    }

    public int size(){
        return tiles.size();
    }

    public Hive.Player cellOwner() {
        if(tileOwnership.size() == 0){
            return null;
        }
        return tileOwnership.get(tileOwnership.size() - 1);
    }

    public int getCoordinate_R() {
        return Coordinate_R;
    }

    public void setCoordinate_R(int coordinate_R) {
        Coordinate_R = coordinate_R;
    }

    public int getCoordinate_Q() {
        return Coordinate_Q;
    }

    public void setCoordinate_Q(int coordinate_Q) {
        Coordinate_Q = coordinate_Q;
    }

    public Cell clone() throws CloneNotSupportedException {
        return (Cell)super.clone();
    }
}
