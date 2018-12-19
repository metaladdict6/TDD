package nl.hanze.hive;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRuleChecker implements RuleChecker {

    private Game game;

    public GameRuleChecker(Game game) {
        this.game = game;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        Cell originalCell = game.getCell(fromQ, fromR);
        if (!game.queenPlayed()) {
            throw new GameExceptions.GameMoveBeforeQueenPlacement("You have to place your Queen before moving your pieces");
        } else if(!hasOneNeighbour(fromQ, fromR, toQ, toR)){
            throw new GameExceptions.GameMoveToSpaceWithoutNeighboursException("You have to move your piece next to another piece.");
        } else if(originalCell.size() == 0) {
            throw new Hive.IllegalMove("Nothing to move");
        } else if (originalCell.cellOwner() != game.currentPlayer){
            throw new Hive.IllegalMove("You cannot move the piece of another player!");
        } else if(calculateDepthOfStep(fromQ,  fromR,  toQ, toR) > 2) {
            throw new GameExceptions.GameToBigHeighDifferenceException("You can only step one level up or down");
        } else {
            return originalCell;
        }
    }

    /**
     * This method checks if the destination has the minimum of one neighbour.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return
     */
    private boolean hasOneNeighbour(int fromQ, int fromR, int toQ, int toR) {
        ArrayList<Cell> neighbours = game.findNeighbours(toQ, toR);
        Cell currentCell = game.getCell(fromQ, fromR);
        boolean twoLevels = currentCell.size() > 1;
        int amountOfNeighbours = 0;
        for (Cell cell: neighbours) {
            if (!cell.equals(currentCell) || twoLevels){
                if (cell.cellOwner() != null) {
                    amountOfNeighbours++;
                }
            }
        }
        return amountOfNeighbours > 0;
    }

    /**
     * This method checks if the dept of the move isn't too deep
     * @param fromQ The horizontal starting position
     * @param fromR The vertical starting position.
     * @param toQ The horizontal destination position.
     * @param toR The vertical destination position.
     * @return
     */
    private int calculateDepthOfStep(int fromQ, int fromR, int toQ, int toR) {
        HashMap<Integer, HashMap<Integer, Cell>> grid = this.game.getGrid();
        Cell current = this.game.getCell(fromQ, fromR);
        Cell destination = this.game.getCell(toQ, toR);
        int currentSize = current.size();
        int desinationSize = destination.size();
        if (currentSize == desinationSize) {
            return 0;
        }
        else if (currentSize > desinationSize) {

            return  currentSize - desinationSize;
        }else {
            return currentSize - desinationSize;
        }
    }
}
