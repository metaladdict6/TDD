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

    private HashMap<Hive.Tile, RuleChecker> moveCheckers;

    /**
     * The move handle wil handle any moves of the game. It will also throw the necessary exceptions.
     * @param game The game the handler wil handle moves for.
     */
    MoveHandler(Game game) {
        this.game = game;
        this.generic = new GameRuleChecker(game);
        this.moveCheckers = new HashMap<>();
        this.moveCheckers.put(Hive.Tile.BEETLE, new BeetleRuleChecker(game, this));
        this.moveCheckers.put(Hive.Tile.SPIDER, new SpiderRuleChecker(game, this));
        this.moveCheckers.put(Hive.Tile.GRASSHOPPER, new GrassHopperRuleChecker(game, this));
        this.moveCheckers.put(Hive.Tile.QUEEN_BEE, new QueenRuleChecker(game, this));
        this.moveCheckers.put(Hive.Tile.SOLDIER_ANT, new SoldierAntRuleChecker(game, this));
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
        if (moveCheckers.containsKey(tile)){
            return this.moveCheckers.get(tile).legalMove(fromQ, fromR, toQ, toR);
        }
        throw new Hive.IllegalMove("There is no valid tile");
    }

    /**
     * This method checks if the move breaks the chain of connected tiles
     * @param fromQ Starting horizontal position.
     * @param fromR Starting vertical position.
     * @return If this breaks the chain or not.
     */
    public boolean keepsChainConnected(int fromQ, int fromR) {
        HashMap<Integer, HashMap<Integer, Cell>> grid = BoardBuilder.copyGrid(game.getGrid());
        HashSet<Cell> occupiedCells = new HashSet<>();
        ArrayList<HashSet<Cell>> everybodysNeighbours = new ArrayList<>();
        Cell origin = game.getCell(fromQ, fromR);
        for (HashMap<Integer, Cell> row: grid.values()) {
            for (Integer key : row.keySet()) {
                Cell cell = row.get(key);
                if (cell.size() != 0) {
                    if(!(cell.getCoordinateQ() == fromQ && cell.getCoordinateR() == fromR)){
                        occupiedCells.add(cell);
                    }
                }
            }
        }
        for(Cell cell: occupiedCells) {
            HashSet<Cell> theseNeighbours = new HashSet<>();
            everybodysNeighbours.add(theseNeighbours);
            for(Cell neighbour: game.findNeighbours(cell)){
                if(!neighbour.equals(origin)){
                    findAllNeighbours(theseNeighbours, origin, neighbour);
                }
            }
        }
        int chainLength = everybodysNeighbours.get(0).size();
        for(HashSet<Cell> cells: everybodysNeighbours){
            if(chainLength != cells.size()){
                return false;
            }
        }
        return true;
    }

    private void findAllNeighbours(HashSet<Cell> neighbours, Cell origin, Cell current){
        for(Cell cell: game.findNeighbours(current)){
            if(cell.occupied() && !neighbours.contains(cell) && !cell.equals(origin)){
                neighbours.add(cell);
                findAllNeighbours(neighbours, origin, cell);
            }
        }
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
    Double calculateDistance(int fromQ, int fromR, int toQ, int toR) {
        double toQMinusFromQ = toQ - fromQ;
        double toQMinusFromQPowerTwo = Math.pow(toQMinusFromQ, 2);
        double toRMinusFromR = toR - fromR;
        double toRMinusFromRPowerTwo = Math.pow(toRMinusFromR, 2);
        double absoluteValue = toQMinusFromQPowerTwo + toRMinusFromRPowerTwo;
        return Math.sqrt(absoluteValue);
    }

    boolean isNeighbour(int fromQ, int fromR, int toQ, int toR){
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

    Cell checkTileSpecificRules(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove{
        return this.generic.legalMove(fromQ, fromR, toQ, toR);
    }



}

