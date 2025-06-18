package model.domain.token.base;

import model.domain.board.Board;
import model.domain.board.Position;
import model.domain.board.PositionDirection;

import java.util.List;

public abstract class Token implements IToken {
    protected Position position;

    protected Token() {
    }


    public boolean isMovable() {
        return false;
    }

    public boolean isTurnable() {
        return false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPlaced() {
        return position != null; // Token is placed if it has a position
    }

    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        return beamPath; // Default implementation returns the beam path unchanged, as beam hits token and stops
    }

    public boolean isTouchRequired() {
        return true; // Default implementation, can be overridden by subclasses
    }

    public boolean isTouched(PositionDirection positionDirection) {
        if (!positionDirection.getPosition().equals(this.position)) {
            return true; // The token is touched if the position matches
        }
        for (Position neighbour : this.position.getNeighbours()) { // Match neighbours of the token's position
            if (neighbour.equals(positionDirection.getPosition())) {
                switch (positionDirection.getDirection()) { // Beam then touches if direction goes towards the token
                    case UP -> {
                        return neighbour.getY() > this.position.getY(); // Beam touches if direction is UP and beam is below the token
                    }
                    case DOWN -> {
                        return neighbour.getY() < this.position.getY(); // Beam touches if direction is DOWN and beam is above the token
                    }
                    case LEFT -> {
                        return neighbour.getX() > this.position.getX(); // Beam touches if direction is LEFT and beam is right of the token
                    }
                    case RIGHT -> {
                        return neighbour.getX() < this.position.getX(); // Beam touches if direction is RIGHT and beam is left of the token
                    }
                }
            }
        }
        return false;
    }

}
