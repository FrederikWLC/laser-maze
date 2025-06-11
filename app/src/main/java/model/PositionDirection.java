package model;

public record PositionDirection(Position getPos, Direction getDir) {
    public PositionDirection increment() {
        return new PositionDirection(
            new Position(getPos().getX() + getDir().getDx(), getPos().getY() + getDir().getDy()),
                getDir()
        );
    }
}
