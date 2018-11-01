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
        Cell fromCell = genericRulesChecker(fromQ, fromR, toQ, toR);
        Cell toCell = checkTileSpecificeRules(fromQ, fromR, toQ, toR, fromCell);
        if (toCell == null) {
            throw new Hive.IllegalMove("TO CELL IS NULL");
        }
        toCell.add(game.currentPlayer, fromCell.pop());
    }

    private Cell checkTileSpecificeRules(int fromQ, int fromR, int toQ, int toR, Cell fromCell) throws Hive.IllegalMove {
        Hive.Tile tile = fromCell.getTopTile();
        switch (tile){
            case BEETLE:
                return moveBeetle(fromQ, fromR, toQ, tile);
            case GRASSHOPPER:
                return moveGrasshopper(fromQ, fromR, toQ, tile);
            case QUEEN_BEE:
                return moveQueen(fromQ, fromR, toQ, toR, tile);
            case SPIDER:
                return moveSpider(fromQ, fromR, toQ, toR, tile);
            case SOLDIER_ANT:
                return moveSoldierAnt(fromQ, fromR, toQ, toR, tile);
        }
        throw new Hive.IllegalMove("There is no valid tile");
    }


    /**
     * This method checks for the rules that apply to every tile.
     * @param fromQ Starting horizontal position.
     * @param fromR Starting vertical position.
     * @param toQ Destination horizontal position
     * @param toR Destination vertical position.
     * @return The from cell that will be poped if every other check also works.
     * @throws Hive.IllegalMove
     */
    private Cell genericRulesChecker(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell fromCell = game.getCell(fromQ, fromR);
        if (!game.queenPlayed()){
            throw new Hive.IllegalMove("You have to place your Queen before moving your pieces");
        }else if(!followsMoveRules(fromQ, fromR, toQ, toR)){
            throw new Hive.IllegalMove("You have to move your piece next to another piece.");
        }else if(fromCell.size() == 0) {
            throw new Hive.IllegalMove("Nothing to move");
        }else if (fromCell.cellOwner() != game.currentPlayer){
            throw new Hive.IllegalMove("You cannot move the piece of another player!");
        // } else if(checkIfBreaksTileChain(fromQ, fromR, toQ, toR)) {
        //    throw new Hive.IllegalMove("You cannot break the tile chain!");
        }else {
            return fromCell;
        }
    }

    /**
     * This method checks if the move breaks the
     * @param fromQ Starting horizontal position.
     * @param fromR Starting vertical position.
     * @param toQ Destination horizontal position
     * @param toR Destination vertical position.
     * @return If this breaks the chain or not.
     */
    private boolean checkIfBreaksTileChain(int fromQ, int fromR, int toQ, int toR) {
        HashMap<Integer, HashMap<Integer, Cell>> grid = copyGrid();
        Cell current = grid.get(fromR).get(fromQ);
        grid.get(toR).get(toQ).add(game.currentPlayer, current.pop());
        for(Integer key: grid.keySet()) {
            HashMap<Integer, Cell> row = grid.get(key);
            for(Integer rowKey: row.keySet()) {
                Cell cell = row.get(rowKey);
                if (cell.size() != 0) {
                    ArrayList<Cell> neighbours = game.findNeighbours(cell.getCoordinate_Q(), cell.getCoordinate_R(), grid);
                    if (neighbours.size() == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method copies the whole grid so that we can test for a situation without changing the game state.
     * @return A full copy of the current game grid.
     */
    private HashMap<Integer, HashMap<Integer, Cell>> copyGrid() {
        HashMap<Integer, HashMap<Integer, Cell>> gridCopy = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        for(Integer key: grid.keySet()){
            HashMap<Integer, Cell> rowCopy = new HashMap<>();
            gridCopy.put(key, rowCopy);
            HashMap<Integer, Cell> row = grid.get(key);
            for(Integer rowKey: row.keySet()) {
                try {
                    rowCopy.put(rowKey, row.get(rowKey).clone());
                }catch (CloneNotSupportedException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
        return gridCopy;
    }

    private Cell moveBeetle(int fromQ, int fromR, int toQ, Hive.Tile tile) throws Hive.IllegalMove {
        return null;
    }

    /**
     * This method return the cell the Soldier ant will be moved to if the move doesn't break any rules.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return
     * @throws Hive.IllegalMove
     */
    private Cell moveSoldierAnt(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove {
        Cell cell = game.getCell(toQ, toR);
        if (fromQ == toQ && fromR == toR) {
            throw new SoldierAntMoveException("You may not move to the same space");
        }else if(cell.cellOwner() != null) {
            throw new SoldierAntMoveException("You cannot move to an occupied space");
        }
        return cell;
    }

    private Cell moveGrasshopper(int fromQ, int fromR, int toQ, Hive.Tile tile) throws Hive.IllegalMove {
        return null;
    }

    /**
     * This method moves the Queen and throws an exception if the move does not follow the rules.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return Cell The cell the Queen is going to move to.
     * @throws Hive.IllegalMove The throw Exception if the move is invalid.
     */
    private Cell moveQueen(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove {
        if (calculateMovesBetweenCells(fromQ, fromR, toQ, toR) > 1) {
            throw new QueenMoveException("You cannot move more then one tile!");
        }
        Cell cell = game.getCell(toQ, toR);
        if (cell.size() > 0) {
            throw new QueenMoveException("You cannot move to an occupied space.");
        }
        this.game.setWhiteQueenCell(cell);
        return cell;
    }


    /**
     * This method moves the Spider and throws an exception if the move does not follow the rules.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return Cell The cell the Spider is going to move to.
     * @throws Hive.IllegalMove
     */
    private Cell moveSpider(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove {
        return null;
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
