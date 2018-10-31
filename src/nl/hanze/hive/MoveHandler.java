package nl.hanze.hive;

import java.util.ArrayList;

/**
 * Created by robert on 31-10-18.
 */
public class MoveHandler {

    private Game game;

    /**
     * The move handle wil handle any moves of the game. It will also throw the neccesary exceptions.
     * @param game The game the handler wil handle moves for.
     */
    public MoveHandler(Game game) {
        this.game = game;
    }

    public void moveTile(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if (!game.queenPlayed()){
            throw new Hive.IllegalMove("You have to place your Queen before moving your pieces");
        }else if(!followsMoveRules(fromQ, fromR, toQ, toR)){
            throw new Hive.IllegalMove("You have to move your piece next to another piece.");
        }
        Cell fromCell = game.getCell(fromQ, fromR);
        if (fromCell.cellOwner() != game.currentPlayer) {
            throw new Hive.IllegalMove("You cannot move the piece of another player!");
        }
        Hive.Tile tile = fromCell.pop();
        switch (tile){
            case BEETLE:
                break;
            case GRASSHOPPER:
                moveGrasshopper(fromQ, fromR, toQ, tile);
                break;
            case QUEEN_BEE:
                moveQueen(fromQ, fromR, toQ, toR, tile);
                break;
            case SPIDER:
                moveSpider(fromQ, fromR, toQ, toR, tile);
                break;
            case SOLDIER_ANT:
                moveSoldierAnt(fromQ, fromR, toQ, toR, tile);
                break;
        }
    }

    private void moveSoldierAnt(int fromQ, int fromR, int toQ, int toR, Hive.Tile tile) throws Hive.IllegalMove{
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

    public boolean followsMoveRules(int fromQ, int fromR, int toQ, int toR) {
        ArrayList<Cell> neighbours = game.findNeighbours(toQ, toR);
        int neighoursAmount = 0;
        for(Cell cell: neighbours) {
            if(cell.getCoordinate_R() != fromR && cell.getCoordinate_Q() != fromQ){
                if (cell.cellOwner() != null) {
                    neighoursAmount++;
                }
            }
        }
        return neighoursAmount > 0;
    }
}
