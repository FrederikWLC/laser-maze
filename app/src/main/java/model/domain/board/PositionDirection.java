package model.domain.board;

import java.util.Objects;

public class PositionDirection {
    private final Position position;
    private final Direction direction;

    public PositionDirection(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position getPosition() { return position; }
    public Direction getDirection() { return direction; }

    public PositionDirection increment() {
        return new PositionDirection(
                new Position(
                        position.getX() + direction.getDx(),
                        position.getY() + direction.getDy()
                ),
                direction
        );
    }

    public PositionDirection withDirection(Direction newDirection) {
        return new PositionDirection(position, newDirection);
    }

    public PositionDirection opposite() {
        return new PositionDirection(position, direction.opposite());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionDirection pd)) return false;
        return Objects.equals(position, pd.position) && Objects.equals(direction, pd.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }

    @Override
    public String toString() {
        return "PositionDirection{position=" + position + ", direction=" + direction + "}";
    }
}

