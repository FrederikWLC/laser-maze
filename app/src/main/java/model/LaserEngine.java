package model;

import java.util.ArrayList;
import java.util.List;

public class LaserEngine {

    public List<PositionDirection> fire (LaserToken laser, Board board) {
        if (!laser.isActive()) {
            return List.of();
        }

        List<PositionDirection> beamPath = new ArrayList<>();
        PositionDirection currentPositionDirection = new PositionDirection(laser.getPosition(), laser.getDirection());

        return travel(currentPositionDirection, beamPath, board);
    }

    private List<PositionDirection> travel (PositionDirection currentPositionDirection, List<PositionDirection> beampath, Board board) {
        List<PositionDirection> beamPath = beampath;
        while (true) {
            // Move in the current direction
            currentPositionDirection = currentPositionDirection.increment();

            // Get current tile based on the new position
            Tile tile = board.getTile(currentPositionDirection.getPos().getX(), currentPositionDirection.getPos().getY());

            if (tile == null || beamPath.contains(currentPositionDirection)) {
                break; // Stop if we go out of bounds, or if we revisit a position-direction
            }

            if (!tile.isEmpty()) {
                beamPath = interact(currentPositionDirection,beamPath, board, tile.getToken());
                break; // Stop if we go out of bounds
            }

            beamPath.add(currentPositionDirection);

        }

        return beamPath;
    }

    private List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beampath, Board board, Token token) {
        List<PositionDirection> beamPath = beampath;
        switch (token) {
            case CellBlockerToken cellBlocker -> {
                beamPath.add(currentPositionDirection);
                return travel(currentPositionDirection, beampath, board);
            }
            default -> {
                // Handle other token types if necessary
                return beampath;
            }
        }
    }
}
