package model.domain.engine;

import model.domain.board.PositionTurn;
import model.domain.engine.util.BeamPathHelper;
import model.domain.token.impl.LaserToken;
import model.domain.board.Tile;
import model.domain.token.base.Token;
import model.domain.board.Board;
import model.domain.token.base.ICheckpointToken;
import model.domain.token.base.ITargetToken;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LaserEngine {

    private BeamPathHelper beamPathHelper = new BeamPathHelper();
    private final LaserToken laser;
    private final Board board;
    private List<PositionTurn> lastBeamPath = List.of();

    private List<Token> tokensHit = new ArrayList<>();
    private List<ITargetToken> targetsLit = new ArrayList<>();
    private List<ICheckpointToken> checkpointsChecked = new ArrayList<>();

    public LaserEngine(LaserToken laser, Board board) {
        this.laser = laser;
        this.board = board;
    }

    public void fire() {
        refreshBeamPath();
        if (laser.isActive()) {
            System.out.println("Laser direction: " + laser.getDirection());
            PositionTurn currentPositionTurn = new PositionTurn(laser.getPosition(), laser.getDirection(), laser.getDirection());
            lastBeamPath = travelFrom(currentPositionTurn,List.of());
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
                beamPath = tokenHit.interact(this, positionTurn, beamPath);
                this.addTokentHit(tokenHit);
                break; // Stop the beam if it hits a token
            }

            // If the tile is empty, we continue moving in the current direction
            beamPath = beamPathHelper.addToBeamPath(beamPath, positionTurn);
        }
        return beamPath;
    }

    public BeamPathHelper getBeamPathHelper() {
        return beamPathHelper;
    }

    public List<Token> getTokensHit() {
        return tokensHit;
    }

    public List<ITargetToken> getTargetsLit() {
        return targetsLit;
    }

    public List<ITargetToken> getRequiredTargetsLit()
    {
        return getTargetsLit().stream().
                filter(ITargetToken::isRequiredTarget)
                .toList();
    }

    public List<Token> getTouchRequiredTokensHit() {
        return getTokensHit().stream()
                .filter(Token::isTouchRequired)
                .collect(toList());
    }

    public int getTargetLitNumber() {
        return getTargetsLit().size();
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
        tokensHit = new ArrayList<>();
        targetsLit = new ArrayList<>();
        checkpointsChecked = new ArrayList<>();
    }

}
