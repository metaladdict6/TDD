package nl.hanze.hive;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robert on 31-10-18.
 */
public class MoveHandler {

    private Game game;

    /**
     * The move handle wil handle any moves of the game. It will also throw the neccesary exceptions.
     * @param game The game the handler wil handle moves for.
     */
    MoveHandler(Game game) {
        this.game = game;
    }

    void moveTile(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell fromCell = followGenericRules(fromQ, fromR, toQ, toR);
        Hive.Tile tile = fromCell.getTopTile();
        switch (tile){
            case BEETLE:
                moveBeetle(fromQ, fromR, toQ, tile);
                break;
            case GRASSHOPPER:
                moveGrasshopper(fromQ, fromR, toQ, tile);
                break;
            case QUEEN_BEE:
                moveQueen(fromQ, fromR, toQ, toR, tile);
                fromCell.pop();
                break;
            case SPIDER:
                moveSpider(fromQ, fromR, toQ, toR, tile);
                break;
            case SOLDIER_ANT:
                moveSoldierAnt(fromQ, fromR, toQ, toR, tile);
                break;
        }
        fromCell.pop();
    }

    private Cell followGenericRules(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if (!game.queenPlayed()){
            throw new Hive.IllegalMove("You have to place your Queen before moving your pieces");
        }else if(!followsMoveRules(fromQ, fromR, toQ, toR)){
            throw new Hive.IllegalMove("You have to move your piece next to another piece.");
        }
        Cell fromCell = game.getCell(fromQ, fromR);
        if (fromCell.cellOwner() != game.currentPlayer){
            throw new Hive.IllegalMove("You cannot move the piece of another player!");
        }
        if(checkIfBreaksTileChain(fromQ, fromR, toQ, toR)) {

        }
        return fromCell;
    }

    private boolean checkIfBreaksTileChain(int fromQ, int fromR, int toQ, int toR) {
        HashMap<Integer, HashMap<Integer, Cell>> gridCopy = new  HashMap<Integer, HashMap<Integer, Cell>>();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        for(Integer key: grid.keySet()){
            HashMap<Integer, Cell> rowCopy = new HashMap<Integer, Cell>();
            gridCopy.put(key, rowCopy);

            for(Integer rowKey: grid.get(key).keySet()) {

            }
        }

        return false;
    }

    private void moveBeetle(int fromQ, int fromR, int toQ, Hive.Tile tile) throws Hive.IllegalMove {

    }

    private void moveSoldierAnt(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove {
    }

    private void moveGrasshopper(int fromQ, int fromR, int toQ, Hive.Tile tile) throws Hive.IllegalMove {
    }

    /**
     * This method moves the Queen and throws an exception if the move does not follow the rules.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @throws Hive.IllegalMove The throw Exception if the move is invalid.
     */
    private void moveQueen(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove {
        if (calculateMovesBetweenCells(fromQ, fromR, toQ, toR) > 1) {
            throw new QueenMoveException("You cannot move more then one tile!");
        }
        Cell cell = game.getCell(toQ, toR);
        if (cell.size() > 0) {
            throw new QueenMoveException("You cannot move to an occupied space.");
        }
        cell.add(game.currentPlayer, tile);
        this.game.setWhiteQueenCell(cell);
    }


    /**
     * This method moves the Spider and throws an exception if the move does not follow the rules.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @throws Hive.IllegalMove
     */
    private void moveSpider(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove {

    }

    private boolean followsMoveRules(int fromQ, int fromR, int toQ, int toR) {
        ArrayList<Cell> neighbours = game.findNeighbours(toQ, toR);
        int neighoursAmount = 0;
        for(Cell cell: neighbours) {
            if(cell.getCoordinate_R() != fromR || cell.getCoordinate_Q() != fromQ){
                if (cell.cellOwner() != null) {
                    neighoursAmount++;
                }
            }
        }
        return neighoursAmount > 0;
    }

    private int calculateMovesBetweenCells(int fromQ, int fromR, int toQ, int toR) {
        double absoluteValue = (toQ - fromQ)^2 + (toR - fromR)^2;
        Double result = Math.sqrt(absoluteValue);
        return result.intValue();
    }
}
