package nl.hanze.hive;

import java.util.*;

public class SoldierAntRuleChecker implements RuleChecker {


    private Game game;

    private MoveHandler moveHandler;

    public SoldierAntRuleChecker(Game game, MoveHandler moveHandler) {
        this.game = game;
        this.moveHandler = moveHandler;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        Cell cell = game.getCell(toQ, toR);
        if (fromQ == toQ && fromR == toR) {
            throw new SoldierAntMoveException.SoldierAntMoveToSameSpaceException("You may not move to the same space");
        } else if(cell.cellOwner() != null) {
            throw new SoldierAntMoveException.SoldierAntMoveToOccupiedSpace("You cannot move to an occupied space");
        } else if(!soldierAntCanMove(fromQ, fromR, toQ, toR)) {
            throw new SoldierAntMoveException.SoldierAntNoPathToDesinationException("No path to destination!");
        }else if(!moveHandler.everythingIsConnected(fromQ, fromR)){
            throw new GameBreakTileChainException("Breaks chain at beetle move");
        }
        return cell;
    }

    /**
     * This method checks the solider ant can make it's way to it's destination.
     * It does this moving along the cell's that are closest to it's destination.
     * If it cannot move without turning back, it returns an exception.
     * This is guaranteed by a HashSet that keeps count of the visited cells.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return A boolean representing if the ant can actually make the move.
     */
    private boolean soldierAntCanMove(int fromQ, int fromR, int toQ, int toR) {
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        HashSet<Cell> visited = new HashSet<>();
        Cell currentCell = game.getCell(fromQ, fromR);
        Cell desintation = game.getCell(toQ, toR);
        visited.add(currentCell);
        while (!currentCell.equals(desintation)) {
            HashMap<Double, Cell> options = new HashMap<>();
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinateQ(), currentCell.getCoordinateR());
            for (Cell neighbour : neighbours) {
                if (neighbour.equals(desintation)) {
                    return true;
                } else if (neighbour.cellOwner() == null) {
                    if (!visited.contains(neighbour)){
                        ArrayList<Cell> neighboursOfNeighbours =
                                game.findNeighbours(neighbour.getCoordinateQ(),
                                neighbour.getCoordinateR());
                        boolean hasOneNeighbour = false;
                        int neighboursCount = 0;
                        for (Cell neighbourOption: neighboursOfNeighbours) {
                            if (neighbourOption.size() != 0 ) {
                                hasOneNeighbour = true;
                                neighboursCount++;
                            }
                        }
                        if (hasOneNeighbour) {
                            options.put(moveHandler.calculateDistance(neighbour.getCoordinateQ(), neighbour.getCoordinateR(),
                                    desintation.getCoordinateQ(), desintation.getCoordinateR()), neighbour);
                        }
                    }

                }
            }
            Set<Double> keys = options.keySet();
            if (keys.size() == 0) {
                return false;
            }
            double key = Collections.min(keys);
            currentCell = options.get(key);
            visited.add(currentCell);
        }
        return false;
    }
}
