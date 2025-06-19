package view.dto;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;

public class ViewOnlyToken extends Token implements ITurnableToken {
    private final Direction direction;

    public ViewOnlyToken(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) { }

    @Override
    public boolean isTurnable() {
        return false;
    }

    @Override
    public boolean isTurned() {
        return false;
    }

    @Override
    public void setTurnable(boolean turnable) { }
}


