package nl.hanze.hive;

public class BeetleRuleChecker implements RuleChecker {

    private Game game;

    private MoveHandler moveHandler;

    public BeetleRuleChecker(Game game, MoveHandler moveHandler) {
        this.game = game;
        this.moveHandler = moveHandler;
    }

    @Override
    public Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Cell cell = game.getCell(toQ, toR);
        if(!moveHandler.isNeighbour(fromQ, fromR, toQ, toR)) {
            throw new BeetleMoveException("You cannot move the beetle more then one cell!");
        }else if(fromQ == toQ && fromR == toR){
            throw new BeetleMoveException("You cannot move to the same space!");
        }else if(!moveHandler.everythingIsConnected(fromQ, fromR)){
            throw new GameBreakTileChainException("Breaks chain at beetle move");
        }
        return cell;
    }
}
