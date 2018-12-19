package nl.hanze.hive;

public class QueenRuleChecker implements RuleChecker {

    private final Game game;

    private MoveHandler moveHandler;

    public QueenRuleChecker(Game game, MoveHandler moveHandler) {
        this.game = game;
        this.moveHandler = moveHandler;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if (!moveHandler.isNeighbour(fromQ, fromR, toQ, toR)) {
            throw new QueenMoveException.QueenMoveTooFarException("You cannot move more then one tile!");
        }
        Cell cell = game.getCell(toQ, toR);
        if (cell.size() > 0) {
            throw new QueenMoveException.QueenMoveToOccupiedSpaceException("You cannot move to an occupied space.");
        }
        return cell;
    }


}
