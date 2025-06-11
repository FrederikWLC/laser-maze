package model;

public class CellBlockerToken extends Token {
    public CellBlockerToken(Position position) {
        super(position,Direction.DOWN);
        mutable = false; // The Cell Blocker is immutable
    }
}

