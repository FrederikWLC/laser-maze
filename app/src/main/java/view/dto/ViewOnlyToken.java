package view.dto;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;

public class ViewOnlyToken extends Token implements ITurnableToken {
    private final Direction direction;
    private final boolean turnable;
    private final boolean movable;

    public ViewOnlyToken(Direction direction, boolean turnable, boolean movable) {

        this.direction = direction;
        this.turnable = turnable;
        this.movable = movable;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) { }

    @Override
    public boolean isTurnable() {

        return turnable;
    }
    @Override
    public boolean isMovable() {
        return movable;
    }

    @Override
    public boolean isTurned() {
        return false;
    }

    @Override
    public void setTurnable(boolean turnable) { }
}


