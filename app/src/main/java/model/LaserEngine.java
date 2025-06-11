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

        while (true) {

            // Move in the current direction
            currentPositionDirection = currentPositionDirection.increment();

            // Get current tile based on the new position
            Tile tile = board.getTile(currentPositionDirection.position().x(), currentPositionDirection.position().y());

            if (tile == null || beamPath.contains(currentPositionDirection)) {
                break; // Stop if we go out of bounds, or if we revisit a position-direction
            }

            beamPath.add(currentPositionDirection);

        }

        return beamPath;
    }
}
