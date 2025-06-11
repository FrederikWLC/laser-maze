package model;

public record PositionDirection(Position position, Direction direction) {
    public PositionDirection increment() {
        return new PositionDirection(
            new Position(position.x() + direction.getDx(), position.y() + direction.getDy()),
            direction
        );
    }
}
