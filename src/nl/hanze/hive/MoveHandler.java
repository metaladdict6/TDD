package nl.hanze.hive;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * This class will handle all the moves in the game.
 * It will check if moves are legal.
 * It will also update the location of the Queen in the game state if the Queen is moved.
 * This class could be expanded as to accept the game as a variable as well, but that isn't the current scope of
 * the project.
 *
 * @author Robert Ziengs, Leon Wetzel
 */
class MoveHandler {

    private Game game;


    private RuleChecker generic;

    /**
     * The move handle wil handle any moves of the game. It will also throw the necessary exceptions.
     * @param game The game the handler wil handle moves for.
     */
    MoveHandler(Game game) {
        this.game = game;
        this.generic = new GameRuleChecker(game);
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
        Cell fromCell = this.generic.legalMove(fromQ, fromR, toQ, toR);
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
        } else {
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
     * This method checks if the move breaks the
     * @param fromQ Starting horizontal position.
     * @param fromR Starting vertical position.
     * @param toQ Destination horizontal position
     * @param toR Destination vertical position.
     * @return If this breaks the chain or not.
     */
    private boolean breaksTileChain(int fromQ, int fromR, int toQ, int toR) {
        HashMap<Integer, HashMap<Integer, Cell>> grid = BoardBuilder.copyGrid(game.getGrid());
//        HashSet<Cell> cellsWithNeighbours = new HashSet<>();
//        for (HashMap<Integer, Cell> row: grid.values()) {
//            for(Integer key: row.keySet()) {
//                Cell cell = row.get(key);
//                if (cell.size() != 0) {
//                    for (Cell neighbour: game.findNeighbours(cell.getCoordinateQ(), cell.getCoordinateR(), grid)) {
//                        if (neighbour.size() > 0) {
//                            cellsWithNeighbours.add(cell);
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        Cell current = grid.get(fromR).get(fromQ);
//        cellsWithNeighbours.remove(current);
//        Cell destination = grid.get(toR).get(toQ);
//        destination.add(game.currentPlayer, current.pop());
//        cellsWithNeighbours.add(destination);
//        ArrayList<Cell> cells = new ArrayList<>();
//        for (HashMap<Integer, Cell> row: grid.values()) {
//            for(Cell cell: row.values()) {
//                if (cell.size() != 0) {
//                    cells.add(cell);
//                }
//            }
//        }
//        for (Cell cell: cells) {
//            if (cellsWithNeighbours.contains(cell)) {
//                boolean hasNeighbour = false;
//                for (Cell neighbour: game.findNeighbours(cell.getCoordinateQ(), cell.getCoordinateR(), grid)) {
//                    if (neighbour.size() > 0) {
//                        hasNeighbour = true;
//                    }
//                }
//                if (!hasNeighbour) {
//                    current.add(game.currentPlayer, destination.pop());
//                    return true;
//                }
//            }
//        }
//        current.add(game.currentPlayer, destination.pop());
        return false;
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
        if(!isNeighbour(fromQ, fromR, toQ, toR)) {
            throw new BeetleMoveException("You cannot move the beetle more then one cell!");
        }else if(fromQ == toQ && fromR == toR){
            throw new BeetleMoveException("You cannot move to the same space!");
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
            throw new SoldierAntMoveException.SoldierAntMoveToSameSpaceException("You may not move to the same space");
        } else if(cell.cellOwner() != null) {
            throw new SoldierAntMoveException.SoldierAntMoveToOccupiedSpace("You cannot move to an occupied space");
        } else if(!soldierAntCanMove(fromQ, fromR, toQ, toR)) {
            throw new SoldierAntMoveException.SoldierAntNoPathToDesinationException("No path to destination!");
        }
        return cell;
    }

    /**
     * Moves a grasshopper from one location to another.
     * @param fromQ
     * @param fromR
     * @param toQ
     * @param toR
     * @return
     * @throws Hive.IllegalMove
     */
    private Cell moveGrasshopper(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
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
        return destination;
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
        if (!isNeighbour(fromQ, fromR, toQ, toR)) {
            throw new QueenMoveException.QueenMoveTooFarException("You cannot move more then one tile!");
        }
        Cell cell = game.getCell(toQ, toR);
        if (cell.size() > 0) {
            throw new QueenMoveException.QueenMoveToOccupiedSpaceException("You cannot move to an occupied space.");
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
        return cell;
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
                        ArrayList<Cell> neighboursOfNeighbours = game.findNeighbours(neighbour.getCoordinateQ(),
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
                            options.put(calculateDistance(neighbour.getCoordinateQ(), neighbour.getCoordinateR(),
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
                        options.put(calculateDistance(neighbour.getCoordinateQ(), neighbour.getCoordinateR(),
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

    private boolean isNeighbour(int fromQ, int fromR, int toQ, int toR){
        HashMap<Integer, HashMap<Integer, Cell>> grid = game.getGrid();
        Cell destination = game.getCell(toQ, toR);
        ArrayList<Cell> neighbours = game.findNeighbours(fromQ, fromR);
        for (Cell neighbour: neighbours) {
            if (neighbour.equals(destination)) {
                return true;
            }
        }
        return false;
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
            ArrayList<Cell> neighbours = game.findNeighbours(currentCell.getCoordinateQ(), currentCell.getCoordinateR());
            for (Cell neighbour: neighbours) {
                if (neighbour.equals(desintation)) {
                    steps++;
                    if (steps == 1) {
                        throw new GrassHopperMoveException.GrassHopperMoveOnSquareException("You have move more then one square");
                    }
                    return new GrassHopperStepResult(steps, true);
                } else  {
                    options.put(calculateDistance(neighbour.getCoordinateQ(), neighbour.getCoordinateR(),
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

    public RuleChecker getGeneric() {
        return generic;
    }

    public Cell checkTileSpecificRules(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        return this.generic.legalMove(fromQ, fromR, toQ, toR);
    }
}

