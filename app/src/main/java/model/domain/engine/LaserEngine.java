package model.domain.engine;

import model.domain.board.PositionTurn;
import model.domain.engine.util.BeamPathHelper;
import model.domain.token.impl.LaserToken;
import model.domain.board.PositionDirection;
import model.domain.board.Tile;
import model.domain.token.base.Token;
import model.domain.board.Board;
import model.domain.token.base.ICheckpointToken;
import model.domain.token.base.ITargetToken;

import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LaserEngine {

    private BeamPathHelper beamPathHelper = new BeamPathHelper();
    private final LaserToken laser;
    private final Board board;
    private List<PositionTurn> lastBeamPath = List.of();
    private List<Token> tokensHit = List.of();
    private List<ITargetToken> targetsLit = List.of();
    private List<ICheckpointToken> checkpointsChecked = List.of();

    public LaserEngine(LaserToken laser, Board board) {
        this.laser = laser;
        this.board = board;
    }

    public void fire() {
        refreshBeamPath();
        if (laser.isActive()) {
            PositionTurn currentPositionTurn = new PositionTurn(laser.getPosition(), laser.getDirection(), laser.getDirection());
            travelFrom(currentPositionTurn,List.of());
        }
    }

    public List<PositionTurn> travelFrom(PositionTurn positionTurn, List<PositionTurn> beamPath) {
        while (true) {
            // Move in the current direction
            positionTurn = positionTurn.incrementWithStraight();

            // Get current tile based on the new position
            Tile tile;
            try {
                tile = board.getTile(positionTurn.getPosition().getX(), positionTurn.getPosition().getY());
            } catch (IndexOutOfBoundsException e) {
                // If we go out of bounds, we stop the beam
                break;
            }

            if (beamPath.contains(positionTurn)) {
                break; // Stop if we revisit a position-turn to prevent infinite loops
            }

            if (!tile.isEmpty()) {
                Token tokenHit = tile.getToken();
                beamPath = beamPathHelper.addBeamPaths(beamPath, tokenHit.interact(this, positionTurn, beamPath));
                this.addTokentHit(tokenHit);
                break; // Stop the beam if it hits a token
            }

            // If the tile is empty, we continue moving in the current direction
            beamPath = beamPathHelper.addToBeamPath(beamPath, positionTurn);
        }
        return beamPath;
    }

    public List<Token> getTokensHit() {
        return tokensHit;
    }

    public List<ITargetToken> getTargetsLit() {
        return targetsLit;
    }

    public int getTargetLitNumber() {
        return targetsLit.size();
    }

    public List<PositionTurn> getLastBeamPath() {
        return lastBeamPath;
    }

    public void addTokentHit(Token token) {
        tokensHit.add(token);
    }

    public void addTargetLit(ITargetToken token) {
        targetsLit.add(token);
    }

    public void addCheckpointChecked(ICheckpointToken token) {
        checkpointsChecked.add(token);
    }

    public List<ICheckpointToken> getCheckpointsChecked() {
        return checkpointsChecked;
    }

    public void refreshBeamPath() {
        this.lastBeamPath = List.of();
    }

    public List<Token> getAllTouchRequiredTokens(List<PositionDirection> beamPath, List<Token> tokens) {
        return tokens.stream()
                .filter(Token::isTouchRequired)
                .toList();
    }

    public List<Token> getAllTouchRequiredTokensTouched(List<PositionDirection> beamPath, List<Token> tokens) {
        return tokens.stream()
                .filter(Token::isTouchRequired)
                .filter(token ->
                        beamPath.stream().anyMatch(token::isTouched)
                ).toList();
    }

    public boolean areAllTouchRequiredTokensTouched(List<PositionDirection> beamPath, List<Token> tokens) {
        return getAllTouchRequiredTokensTouched(beamPath, tokens).size() == getAllTouchRequiredTokens(beamPath,tokens).size();
    }

    public List<ICheckpointToken> getAllCheckpointsPenetrated(List<PositionDirection> beamPath, List<Token>             tokens) {
        return tokens.stream()
                .filter(ICheckpointToken.class::isInstance)
                .map(ICheckpointToken.class::cast).
                filter(checkpoint ->
                        beamPath.stream().anyMatch(checkpoint::isPenetrated)
                ).toList();
    }

    public boolean areAllCheckpointsPenetrated(List<PositionDirection> beamPath, List<Token> tokens) {
        int totalCheckpoints = tokens.stream()
                .filter(ICheckpointToken.class::isInstance)
                .toList().size();
        return getAllCheckpointsPenetrated(beamPath, tokens).size() == totalCheckpoints;
    }

    public List<ITargetToken> getAllTargetsHit(List<PositionDirection> beamPath, List<Token> tokens) {
        return tokens.stream()
                .filter(ITargetToken.class::isInstance)
                .map(ITargetToken.class::cast)
                .filter(target ->
                        beamPath.stream().anyMatch(target::isHit)
                ).toList();
    }

    public int getTargetHitNumber(List<PositionDirection> beamPath, List<Token> tokens) {
        return getAllTargetsHit(beamPath,tokens).size();
    }

    public List<ITargetToken> getAllRequiredTargetsHit(List<PositionDirection> beamPath, List<Token> tokens)
    {
        return getAllTargetsHit(beamPath, tokens).stream().
                filter(target ->
                        target.isRequiredTarget())
                .toList();
    }

    public boolean areAllRequiredTargetsHit(List<PositionDirection> beamPath, List<Token> tokens) {
        return getAllRequiredTargetsHit(beamPath, tokens).size() == tokens.stream()
                .filter(ITargetToken.class::isInstance)
                .map(ITargetToken.class::cast)
                .filter(ITargetToken::isRequiredTarget)
                .toList().size();
    }

}
