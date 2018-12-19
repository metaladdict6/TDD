package nl.hanze.hive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GrassHopperRuleChecker implements RuleChecker {


    private Game game;

    private MoveHandler moveHandler;

    public GrassHopperRuleChecker(Game game, MoveHandler moveHandler) {
        this.game = game;
        this.moveHandler = moveHandler;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        Cell currentCell = game.getCell(fromQ, fromR);
        Cell destination = game.getCell(toQ, toR);
        GrassHopperStepResult result = requiredGrasshopperSteps(fromQ, fromR, toQ, toR);
        if (currentCell.equals(destination)) {
            throw new GrassHopperMoveException.GrassHopperMoveToSamePlaceException("The grasshopper can't move to the same cell he began");
        } else if(destination.cellOwner() != null) {
            throw new GrassHopperMoveException.GrassHopperMoveToOccupiedSquare("The grasshopper can't move to an occupied space.");
        } else if(!result.isGoesOverPiece()) {
            throw new GrassHopperMoveException.GrassHopperMoveOverUnOccupiedException("The grasshopper has to jump over other pieces");
        } else if(!(result.getSteps() >= 2)) {
            throw new GrassHopperMoveException.GrassHopperMoveOnSquareException("The grasshopper has to jump over at least one piece");
        }
        return destination;    }

    /**
     * This method returns a model with a boolean and a step counter. The stepcounter counnts how many steps it took
     * the grasshopper to reach it's destination. The boolean keeps count of if it actually jump over another piece in
     * the process.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return
     * @throws Hive.IllegalMove
     */
    private GrassHopperStepResult requiredGrasshopperSteps(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        Cell currentCell = game.getCell(fromQ, fromR);
        Cell desintation = game.getCell(toQ, toR);
        int steps = 0;
        boolean crossedPaths = false;
        while (!currentCell.equals(desintation)) {
            HashMap<Double, Cell> options = new HashMap<>();
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinateQ(), currentCell.getCoordinateR());
            for (Cell neighbour: neighbours) {
                if (neighbour.equals(desintation)) {
                    steps++;
                    if (steps == 1) {
                        throw new GrassHopperMoveException.GrassHopperMoveOnSquareException("You have move more then one square");
                    }
                    return new GrassHopperStepResult(steps, true);
                } else  {
                    options.put(moveHandler.calculateDistance(neighbour.getCoordinateQ(), neighbour.getCoordinateR(),
                            desintation.getCoordinateQ(), desintation.getCoordinateR()), neighbour);
                }
            }
            double key = Collections.min(options.keySet());
            Cell closestCell = options.get(key);
            if (closestCell.cellOwner() == null) {
                throw new GrassHopperMoveException.GrassHopperMoveOverUnOccupiedException("You cannot move over unoccupied spaces");
            } else if(!grassHopperTravelingInStraightLine(currentCell.getCoordinateQ(), currentCell.getCoordinateR(),
                    closestCell.getCoordinateQ(), closestCell.getCoordinateR())) {
                throw new GrassHopperMoveException.GrassHopperNotMovingInLineException("You have to move in a straight line!");
            } else {
                steps++;
                currentCell = closestCell;
            }

        }
        return new GrassHopperStepResult(steps, true);
    }

    /**
     * This method makes sure the grasshopper traveled in a straight line.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return
     */
    private boolean grassHopperTravelingInStraightLine(int fromQ, int fromR, int toQ, int toR) {
        if (fromQ == toQ) {
            return true;
        } else if(fromR == toR) {
            return true;
        } else if((fromQ - 1) == toQ && (fromR + 1) == toR) {
            return true;
        } else return (fromQ + 1) == toQ && (fromR - 1) == toR;
    }
}
