package model.domain.token;

import model.domain.board.Direction;

public interface ITurnableToken extends IToken {

    void setTurnable(boolean turnable);

    void setDirection(Direction direction);

    Direction getDirection();

    boolean isTurned();
}
