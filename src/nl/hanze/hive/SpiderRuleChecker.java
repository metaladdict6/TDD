package nl.hanze.hive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SpiderRuleChecker implements RuleChecker {

    private Game game;

    private MoveHandler moveHandler;

    SpiderRuleChecker(Game game, MoveHandler moveHandler) {
        this.game = game;
        this.moveHandler = moveHandler;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        Cell cell = game.getCell(toQ, toR);
        int travelDistance = requiredSpiderSteps(fromQ, fromR, toQ, toR);
        if(fromQ == toQ && fromR == toR) {
            throw new SpiderMoveException.SpiderMoveToSameSpaceException("You cannot move to the same space.");
        } else if(cell.getTopTile() != null) {
            throw new SpiderMoveException.SpiderMoveToOccupiedSpaceException("You cannot move a spider to an occupied space");
        }
        pathingIsValid(travelDistance, fromQ, fromR, toQ, toR);
        return cell;
    }

    private boolean pathingIsValid(int steps, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if(steps == 3){
            return true;
        }
        if(steps < 3 ) {
            if(!foundValidPath(fromQ, fromR, toQ, toR)){
                throw new SpiderMoveException.SpiderMoveNotFarEnoughException("You cannot move less then three cells");
            }
        } else if(steps > 3) {
            if(!foundValidPath(fromQ, fromR, toQ, toR)) {
                throw new SpiderMoveException.SpiderMoveTooFarAwayException("You cannot move more then three cells");
            }
        }
        return true;
    }

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

    private boolean foundValidPath(int fromQ, int fromR, int toQ, int toR) {
        Cell origin = game.getCell(fromQ, fromR);
        Cell destination = game.getCell(toQ, toR);
        for (Cell firstMove: findNeighbours(origin)) {
            if (checkFirstMoveSlideIsLegal(firstMove, origin)) {
                for (Cell secondMove: findNeighbours(firstMove)) {
                    if(checkSecondSlideIsLegal(origin, firstMove, secondMove)) {
                        for (Cell thirdMove: findNeighbours(secondMove)){
                            if(checkThirdSlideIsLegal(origin, firstMove, secondMove, thirdMove)) {
                                if (thirdMove.equals(destination)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkFirstMoveSlideIsLegal(Cell firstMove, Cell origin){
        return notOccupied(firstMove) &&
                cellHasNeighbour(origin, firstMove.getCoordinateQ(), firstMove.getCoordinateR());
    }

    private boolean checkSecondSlideIsLegal(Cell origin, Cell firstMove, Cell secondMove){
        boolean notFirstMove = !firstMove.equals(secondMove);
        boolean notOrigin = !secondMove.equals(origin);
        return notFirstMove &&
                notOrigin &&
                notOccupied(secondMove) &&
                cellHasNeighbour(origin, secondMove.getCoordinateQ(), secondMove.getCoordinateR());
    }

    private boolean checkThirdSlideIsLegal(Cell origin, Cell firstMove, Cell secondMove, Cell thirdMove){
        boolean notFirstMove = !firstMove.equals(thirdMove);
        boolean notSecondMove = !secondMove.equals(thirdMove);
        boolean notOrigin = !thirdMove.equals(origin);
        return  notFirstMove &&
                notSecondMove &&
                notOrigin &&
                notOccupied(thirdMove) &&
                cellHasNeighbour(origin, thirdMove.getCoordinateQ(), thirdMove.getCoordinateR());
    }


    private boolean notOccupied(Cell cell){
        return cell.cellOwner() == null;
    }

    private ArrayList<Cell> findNeighbours(Cell move){
        return game.findNeighbours(move.getCoordinateQ(), move.getCoordinateR());
    }

    /**
     * This method helps to check if one of the slides the spider is going for has a piece adjacent to it.
     * @param origin
     * @param fromQ
     * @param fromR
     * @return
     */
    private boolean cellHasNeighbour(Cell origin, int fromQ, int fromR) {
        for (Cell cell: game.findNeighbours(fromQ, fromR)){
            if(!cell.equals(origin)){
                if(cell.getTopTile() != null){
                    return true;
                }
            }
        }
        return false;
    }
}
