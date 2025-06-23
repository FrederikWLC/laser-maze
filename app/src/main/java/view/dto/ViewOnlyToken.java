package view.dto;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;
import model.domain.token.base.ITargetToken;

public class ViewOnlyToken extends Token implements ITurnableToken, ITargetToken {
    private final Direction direction;
    private final boolean turnable;
    private final boolean movable;
    private final boolean requiredTarget;

    public ViewOnlyToken(Direction direction, boolean turnable, boolean movable, boolean requiredTarget) {
        this.direction = direction;
        this.turnable = turnable;
        this.movable = movable;
        this.requiredTarget = requiredTarget;
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

    public boolean isRequiredTarget() {
        return requiredTarget;
    }

    @Override
    public void setRequiredTarget(boolean required) {
        // no-op since this is view-only
    }


}


