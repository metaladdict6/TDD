package nl.hanze.hive;

import java.util.Stack;

/**
 * Created by rrczi on 9-10-2018.
 * This class will implement
 */
public class Cell extends Stack<Hive.Tile> {

    private int Coordinate_R;

    private int Coordinate_Q;


    public Cell(int coordinateR, int coordinateQ) {
        setCoordinate_R(coordinateR);
        setCoordinate_Q(coordinateQ);
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
}
