package nl.hanze.hive;

public interface RuleChecker {

    Cell legalMove(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove;
}
