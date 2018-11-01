package nl.hanze.hive;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by robert on 31-10-18.
 * This class will handle all the moves in the game.
 * It will check if moves are legal.
 * It will also play tiles and update the location of the Queen in the game state.
 */
class MoveHandler {

    private Game game;

    /**
     * The move handle wil handle any moves of the game. It will also throw the necessary exceptions.
     * @param game The game the handler wil handle moves for.
     */
    MoveHandler(Game game) {
        this.game = game;
    }

    /**
     * This is the generic move method of the class. This will deal with all the moving after the checks have been
     * done that the move is actually legal.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @throws Hive.IllegalMove
     */
    void moveTile(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell fromCell = genericRulesChecker(fromQ, fromR, toQ, toR);
        Cell toCell = checkTileSpecificRules(fromQ, fromR, toQ, toR, fromCell);
        if (toCell == null) {
            throw new Hive.IllegalMove("The cell you want to move to is null");
        }
        Hive.Tile tile = fromCell.getTopTile();
        toCell.add(game.currentPlayer, fromCell.pop());
        if (tile == Hive.Tile.QUEEN_BEE) {
            updateQueen(toCell);
        }
    }


    /**
     * This method update the location of the cell the Queen is located in.
     * This is kept updated so that the isWinner method is easily executed.
     * @param cell The cell the Queen is currently located in.
     */
    public void updateQueen(Cell cell) {
        if (game.currentPlayer == Hive.Player.WHITE) {
            game.setWhiteQueenCell(cell);
        }else {
            game.setBlackQueenCell(cell);
        }
    }

    /**
     * This method call all the method's that deal with any and all generic rules that might occur in the game.
     * It return the cell the player is attemting to move from.
     * @param fromQ Starting horizontal position.
     * @param fromR Starting vertical position.
     * @param toQ Destination horizontal position
     * @param toR Destination vertical position.
     * @return The cell the tile is starting from.
     * @throws Hive.IllegalMove
     */
    public Cell checkTileSpecificRules(int fromQ, int fromR, int toQ, int toR, Cell fromCell) throws Hive.IllegalMove {
        Hive.Tile tile = fromCell.getTopTile();
        switch (tile){
            case BEETLE:
                return moveBeetle(fromQ, fromR, toQ, toR);
            case GRASSHOPPER:
                return moveGrasshopper(fromQ, fromR, toQ, toR);
            case QUEEN_BEE:
                return moveQueen(fromQ, fromR, toQ, toR);
            case SPIDER:
                return moveSpider(fromQ, fromR, toQ, toR);
            case SOLDIER_ANT:
                return moveSoldierAnt(fromQ, fromR, toQ, toR);
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
    public Cell genericRulesChecker(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell fromCell = game.getCell(fromQ, fromR);
        if (!game.queenPlayed()){
            throw new Hive.IllegalMove("You have to place your Queen before moving your pieces");
        }else if(!followsMoveRules(fromQ, fromR, toQ, toR)){
            throw new Hive.IllegalMove("You have to move your piece next to another piece.");
        }else if(fromCell.size() == 0) {
            throw new Hive.IllegalMove("Nothing to move");
        }else if (fromCell.cellOwner() != game.currentPlayer){
            throw new Hive.IllegalMove("You cannot move the piece of another player!");
        //} else if(checkIfBreaksTileChain(fromQ, fromR, toQ, toR)) {
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

    /**
     * This method returns the cell the beetle wants to move to. If it is an illegal move it will throw
     * an exception.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return Cell The cell the Beetle is going to move to.
     * @throws Hive.IllegalMove Will be BeetleMoveException so a proper test can be written for this method.
     */
    private Cell moveBeetle(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell cell = game.getCell(toQ, toR);
        if(calculateDistanceRoundedDown(fromQ, fromR, toQ, toR) > 1) {
            throw new BeetleMoveException("You cannot move the beetle more then one cell!");
        }
        return cell;
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
    private Cell moveSoldierAnt(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell cell = game.getCell(toQ, toR);
        if (fromQ == toQ && fromR == toR) {
            throw new SoldierAntMoveException("You may not move to the same space");
        }else if(cell.cellOwner() != null) {
            throw new SoldierAntMoveException("You cannot move to an occupied space");
        }else if(!soldierAntCanMove(fromQ, fromR, toQ, toR)) {
            throw new SoldierAntMoveException("No path to destination!");
        }
        return cell;
    }

    private Cell moveGrasshopper(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell currentCell = game.getCell(fromQ, fromR);
        Cell desintation = game.getCell(toQ, toR);
        GrassHopperStepResult result = requiredGrasshopperSteps(fromQ, fromR, toQ, toR);
        if (currentCell.equals(desintation)) {
            throw new GrassHopperMoveException("The grasshopper can't move to the same cell he began");
        }else if(desintation.cellOwner() != null) {
            throw  new GrassHopperMoveException("The grasshopper can't move to an occupied space.");
        }else if(!result.isGoesOverPiece()) {
            throw  new GrassHopperMoveException("The grasshopper has to jump over at least one piece");
        }else if(result.getSteps() >= 2) {
            throw  new GrassHopperMoveException("The grasshopper has to jump over at least one piece");
        }
        return desintation;
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
    private Cell moveQueen(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if (calculateDistanceRoundedDown(fromQ, fromR, toQ, toR) > 1) {
            throw new QueenMoveException("You cannot move more then one tile!");
        }
        Cell cell = game.getCell(toQ, toR);
        if (cell.size() > 0) {
            throw new QueenMoveException("You cannot move to an occupied space.");
        }
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
    private Cell moveSpider(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        int travelDistance = requiredSpiderSteps(fromQ, fromR, toQ, toR);
        Cell cell = game.getCell(toQ, toR);
        if(fromQ == toQ && fromR == toR) {
            throw new SpiderMoveException("You cannot move to the same space.");
        }else if(travelDistance < 3) {
            throw new SpiderMoveException("You cannot move less then three cells");
        }else if(travelDistance > 3) {
            throw new SpiderMoveException("You cannot move more then three cells");
        }
        return cell;
    }

    /**
     * This method checks if the destination has the minimum of one neighbour.
     * @param fromQ The origin Q
     * @param fromR The origin R
     * @param toQ The destination Q
     * @param toR The destination R
     * @return
     */
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

    /**
     * This method calculates the distance between two coordinates and rounds it down.
     * This method is used to check if a tile is being moved too far or not far enough.
     * @param fromQ The horizontal starting position
     * @param fromR The vertical starting position.
     * @param toQ The horizontal destination position.
     * @param toR The vertical destination position.
     * @return The distance between these positions.
     */
    private int calculateDistanceRoundedDown(int fromQ, int fromR, int toQ, int toR) {
        return calculateDistance(fromQ, fromR, toQ, toR).intValue();
    }

    /**
     * This method calculates the distance between two coordinates.
     * This method is used to check if a tile is being moved too far or not far enough.
     * @param fromQ The horizontal starting position
     * @param fromR The vertical starting position.
     * @param toQ The horizontal destination position.
     * @param toR The vertical destination position.
     * @return The distance between these positions.
     */
    @NotNull
    private Double calculateDistance(int fromQ, int fromR, int toQ, int toR) {
        double toQMinusFromQ = toQ - fromQ;
        double toQMinusFromQPowerTwo = Math.pow(toQMinusFromQ, 2);
        double toRMinusFromR = toR - fromR;
        double toRMinusFromRPowerTwo = Math.pow(toRMinusFromR, 2);
        double absoluteValue = toQMinusFromQPowerTwo + toRMinusFromRPowerTwo;
        return Math.sqrt(absoluteValue);
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
        Cell currentCell = game.getCell(fromQ, fromR);
        Cell desintation = game.getCell(toQ, toR);
        HashSet<Cell> visisted = new HashSet<>();
        while (!currentCell.equals(desintation)) {
            HashMap<Double, Cell> options = new HashMap<>();
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinate_Q(), currentCell.getCoordinate_R());
            for (Cell neighbour : neighbours) {
                if (neighbour.equals(desintation)) {
                    return true;
                } else if (neighbour.cellOwner() == null && !visisted.contains(neighbour)) {
                    options.put(calculateDistance(neighbour.getCoordinate_Q(), neighbour.getCoordinate_R(),
                            desintation.getCoordinate_Q(), desintation.getCoordinate_R()), neighbour);
                }
            }
            Set<Double> keys = options.keySet();
            if (keys.size() == 0) {
                return false;
            }
            double key = Collections.min(keys);
            currentCell = options.get(key);
        }
        return false;
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
        Cell desintation = game.getCell(toQ, toR);
        int steps = 0;
        while (!currentCell.equals(desintation)) {
            steps++;
            if(steps > 3) {
                return steps;
            }
            HashMap<Double, Cell> options = new HashMap<>();
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinate_Q(), currentCell.getCoordinate_R());
            for (Cell neighbour: neighbours) {
                if (neighbour.equals(desintation)) {
                    return steps;
                }else if(neighbour.cellOwner() != null && false) {
                    options.put(calculateDistance(currentCell.getCoordinate_Q(), currentCell.getCoordinate_R(),
                            neighbour.getCoordinate_Q(), currentCell.getCoordinate_R()), neighbour);
                }else if(neighbour.cellOwner() == null) {
                    options.put(calculateDistance( neighbour.getCoordinate_Q(), currentCell.getCoordinate_R(),
                            desintation.getCoordinate_Q(), desintation.getCoordinate_R()), neighbour);
                }
            }
            double key = Collections.min(options.keySet());
            currentCell = options.get(key);
        }

        return steps;
    }

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
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinate_Q(), currentCell.getCoordinate_R());
            for (Cell neighbour: neighbours) {
                if (neighbour.equals(desintation)) {
                    steps++;
                    return new GrassHopperStepResult(steps, crossedPaths);
                }else if(neighbour.cellOwner() != null) {
                    options.put(calculateDistance(currentCell.getCoordinate_Q(), currentCell.getCoordinate_R(),
                            neighbour.getCoordinate_Q(), currentCell.getCoordinate_R()), neighbour);
                }
            }
            double key = Collections.min(options.keySet());
            Cell closestCell = options.get(key);
            if(closestCell.cellOwner() == null){
                throw new GrassHopperMoveException("You cannot move over unoccupied spaces");
            }else if(!grassHopperTravelingInStraightLine(currentCell.getCoordinate_Q(), currentCell.getCoordinate_Q(),
                    closestCell.getCoordinate_Q(), closestCell.getCoordinate_R())) {
                throw new GrassHopperMoveException("You have to move in a straight line!");
            }else {
                steps++;
                currentCell = closestCell;
            }

        }
        return new GrassHopperStepResult(steps, false);
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
        }else if(fromR == toR) {
            return true;
        }else if((fromQ - 1) == toQ && (fromR + 1) == toR) {
            return true;
        }else if((fromQ + 1) == toQ && (fromR - 1) == toR) {
            return true;
        }
        return false;
    }
}

