package model.domain.engine;

import model.domain.token.LaserToken;
import model.domain.board.PositionDirection;
import model.domain.board.Tile;
import model.domain.token.Token;
import model.domain.board.Board;
import model.domain.token.ICheckpointToken;
import model.domain.token.ITargetToken;

import java.util.List;
import java.util.stream.Stream;

public class LaserEngine {

    public static List<PositionDirection> fire(LaserToken laser, Board board) {
        if (!laser.isActive()) {
            return List.of();
        }

        List<PositionDirection> beamPath = List.of();
        PositionDirection currentPositionDirection = new PositionDirection(laser.getPosition(), laser.getDirection());

        return travel(currentPositionDirection, beamPath, board);
    }

    public static List<PositionDirection> travel(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        while (true) {
            // Move in the current direction
            currentPositionDirection = currentPositionDirection.increment();

            // Get current tile based on the new position
            Tile tile;
            try {
                tile = board.getTile(currentPositionDirection.getPosition().getX(), currentPositionDirection.getPosition().getY());
            } catch (IndexOutOfBoundsException e) {
                // If we go out of bounds, we stop the beam
                break;
            }

            if (beamPath.contains(currentPositionDirection)) {
                break; // Stop if we revisit a position-direction
            }

            if (!tile.isEmpty()) {
                beamPath = tile.getToken().interact(currentPositionDirection, beamPath, board);
                break; // Stop if we go out of bounds
            }

            beamPath = Stream.concat(beamPath.stream(), Stream.of(currentPositionDirection)).toList();

        }

        return beamPath;
    }


    public static List<Token> getAllTouchRequiredTokens(List<PositionDirection> beamPath, List<Token> tokens) {
        return tokens.stream()
                .filter(Token::isTouchRequired)
                .toList();
    }

    public static List<Token> getAllTouchRequiredTokensTouched(List<PositionDirection> beamPath, List<Token> tokens) {
        return tokens.stream()
                .filter(Token::isTouchRequired)
                .filter(token ->
                        beamPath.stream().anyMatch(token::isTouched)
                ).toList();
    }

    public static boolean areAllTouchRequiredTokensTouched(List<PositionDirection> beamPath, List<Token> tokens) {
        return getAllTouchRequiredTokensTouched(beamPath, tokens).size() == getAllTouchRequiredTokens(beamPath,tokens).size();
    }

    public static List<ICheckpointToken> getAllCheckpointsPenetrated(List<PositionDirection> beamPath, List<Token>             tokens) {
        return tokens.stream()
                .filter(ICheckpointToken.class::isInstance)
                .map(ICheckpointToken.class::cast).
                filter(checkpoint ->
                        beamPath.stream().anyMatch(checkpoint::isPenetrated)
                ).toList();
    }

    public static boolean areAllCheckpointsPenetrated(List<PositionDirection> beamPath, List<Token> tokens) {
        int totalCheckpoints = tokens.stream()
                .filter(ICheckpointToken.class::isInstance)
                .toList().size();
        return getAllCheckpointsPenetrated(beamPath, tokens).size() == totalCheckpoints;
    }

    public static List<ITargetToken> getAllTargetsHit(List<PositionDirection> beamPath, List<Token> tokens) {
        return tokens.stream()
                .filter(ITargetToken.class::isInstance)
                .map(ITargetToken.class::cast)
                .filter(target ->
                        beamPath.stream().anyMatch(target::isHit)
                ).toList();
    }

    public static int getTargetHitNumber(List<PositionDirection> beamPath, List<Token> tokens) {
        return getAllTargetsHit(beamPath,tokens).size();
    }

    public static List<ITargetToken> getAllRequiredTargetsHit(List<PositionDirection> beamPath, List<Token> tokens)
    {
        return getAllTargetsHit(beamPath, tokens).stream().
                filter(target ->
                        target.isRequiredTarget())
                .toList();
    }

    public static boolean areAllRequiredTargetsHit(List<PositionDirection> beamPath, List<Token> tokens) {
        return getAllRequiredTargetsHit(beamPath, tokens).size() == tokens.stream()
                .filter(ITargetToken.class::isInstance)
                .map(ITargetToken.class::cast)
                .filter(ITargetToken::isRequiredTarget)
                .toList().size();
    }

}
