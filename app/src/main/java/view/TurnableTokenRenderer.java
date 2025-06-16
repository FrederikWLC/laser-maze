package view;

import model.Direction;

public abstract class TurnableTokenRenderer implements ITurnableTokenRenderer {
    public String getDirString(Direction dir) {
        return switch (dir) {
            case UP -> "NORTH";
            case DOWN -> "SOUTH";
            case LEFT -> "WEST";
            case RIGHT -> "EAST";
        };
    }
}
