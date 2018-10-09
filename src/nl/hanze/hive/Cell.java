package nl.hanze.hive;

import java.util.Stack;

/**
 * Created by rrczi on 9-10-2018.
 * This class will implement
 */
public class Cell extends Stack {

    private int CoordinateY;

    private int CoordinateX;

    public Cell(int coordinateY, int coordinateX) {
        setCoordinateY(coordinateY);
        setCoordinateX(coordinateX);
    }

    public int getCoordinateY() {
        return CoordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        CoordinateY = coordinateY;
    }

    public int getCoordinateX() {
        return CoordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        CoordinateX = coordinateX;
    }
}
