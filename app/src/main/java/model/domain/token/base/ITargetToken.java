package model.domain.token.base;

import model.domain.board.PositionDirection;

public interface ITargetToken extends IToken {

    boolean isRequiredTarget();

    void setRequiredTarget(boolean requiredTarget);

}
