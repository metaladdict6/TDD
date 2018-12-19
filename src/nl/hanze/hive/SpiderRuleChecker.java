package nl.hanze.hive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SpiderRuleChecker implements RuleChecker {

    private Game game;

    private MoveHandler moveHandler;

    public SpiderRuleChecker(Game game, MoveHandler moveHandler) {
        this.game = game;
        this.moveHandler = moveHandler;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        Cell cell = game.getCell(toQ, toR);

        int travelDistance = requiredSpiderSteps(fromQ, fromR, toQ, toR);
        boolean specialRoute = pathFindspider(fromQ, fromR, toQ, toR);

        if(fromQ == toQ && fromR == toR) {
            throw new SpiderMoveException.SpiderMoveToSameSpaceException("You cannot move to the same space.");
        } else if(travelDistance < 3 && !specialRoute) {
            throw new SpiderMoveException.SpiderMoveNotFarEnoughException("You cannot move less then three cells");
        } else if(travelDistance > 3 && !specialRoute) {
            throw new SpiderMoveException.SpiderMoveTooFarAwayException("You cannot move more then three cells");
        }
        if(cell.getTopTile() != null) {
            throw new SpiderMoveException.SpiderMoveToOccupiedSpaceException("You cannot move a spider to an occupied space");
        }
        return cell;    }

    /**
     * This method counts the amount of steps spider needs to make along the board to reach it's destination.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return The amount of steps it would require for the spider to make it to it's destination.
     */
    private int requiredSpiderSteps(int fromQ, int fromR, int toQ, int toR) {
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        Cell currentCell = game.getCell(fromQ, fromR);
        Cell destination = game.getCell(toQ, toR);
        HashSet<Cell> visited = new HashSet<>();
        int steps = 0;
        while (!currentCell.equals(destination)) {
            steps++;
            if (steps > 3) {
                return steps;
            }
            HashMap<Double, Cell> options = new HashMap<>();
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinateQ(), currentCell.getCoordinateR());
            for (Cell neighbour: neighbours) {
                if (neighbour.equals(destination)) {
                    return steps;
                }  else if(neighbour.cellOwner() == null) {
                    if (!visited.contains(neighbour)) {
                        options.put(moveHandler.calculateDistance(neighbour.getCoordinateQ(), neighbour.getCoordinateR(),
                                destination.getCoordinateQ(), destination.getCoordinateR()), neighbour);
                        visited.add(neighbour);
                    }
                }
            }
            double key = Collections.min(options.keySet());
            currentCell = options.get(key);
        }
        return steps;
    }

    private boolean pathFindspider(int fromQ, int fromR, int toQ, int toR) {
        ArrayList<Cell> cells = game.findNeighbours(fromQ, fromR);
        Cell origin = game.getCell(fromQ, fromR);
        Cell desination = game.getCell(toQ, toR);
        for (Cell firstMove: cells) {
            if (firstMove.cellOwner() == null) {
                for (Cell secondMove: game.findNeighbours(firstMove.getCoordinateQ(), firstMove.getCoordinateR())) {
                    if(!firstMove.equals(secondMove) && secondMove != origin && secondMove.cellOwner() == null) {
                        for (Cell thirdMove: game.findNeighbours(secondMove.getCoordinateQ(), secondMove.getCoordinateR())){
                            boolean uniqueThird = thirdMove.equals(firstMove) || secondMove.equals(thirdMove);
                            if(!uniqueThird && thirdMove != origin && thirdMove.cellOwner() == null) {
                                if (thirdMove == desination) {
                                    return  true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
