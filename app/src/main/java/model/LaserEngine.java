package model;

import java.util.ArrayList;
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


}
