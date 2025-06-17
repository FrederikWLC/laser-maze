package model.domain.token;

import model.domain.board.PositionDirection;

public interface ITargetToken extends IToken {

    boolean isRequiredTarget();

    void setRequiredTarget(boolean requiredTarget);

    boolean isHit(PositionDirection beamPositionDirection);
}
