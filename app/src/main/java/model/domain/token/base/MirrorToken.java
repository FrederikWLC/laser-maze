package model.domain.token.base;

import model.domain.board.Direction;

import java.util.EnumMap;
import java.util.Map;

public abstract class MirrorToken extends MutableToken {
    // MirrorToken is a base class for tokens that reflect laser beams.
    // as slashes or backslashes.

    private static final EnumMap<Direction,Direction> BACKSLASH = new EnumMap<>(Map.of(
            Direction.UP,    Direction.LEFT,
            Direction.DOWN,  Direction.RIGHT,
            Direction.LEFT,  Direction.UP,
            Direction.RIGHT, Direction.DOWN
    ));

    private static final EnumMap<Direction,Direction> SLASH = new EnumMap<>(Map.of(
            Direction.UP,    Direction.RIGHT,
            Direction.DOWN,  Direction.LEFT,
            Direction.LEFT,  Direction.DOWN,
            Direction.RIGHT, Direction.UP
    ));

    public boolean isBackSlash() {
        return getDirection() == Direction.UP || getDirection() == Direction.DOWN;
    }

    public boolean isSlash() {
        return getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT;
    }

    public EnumMap<Direction,Direction> getReflectionMap() {
        if (isBackSlash()) {
            return BACKSLASH;
        } else if (isSlash()) {
            return SLASH;
        } else {
            throw new IllegalStateException("MirrorToken direction must be either UP/DOWN or LEFT/RIGHT to get a reflection map");
        }
    }

}

