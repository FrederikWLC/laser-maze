package model.domain.token.builder.base;

import model.domain.board.Direction;
import model.domain.token.base.MutableToken;

public abstract class MutableTokenBuilder<B extends MutableTokenBuilder<B,T>, T extends MutableToken> extends TokenBuilder<B,T> implements IMutableTokenBuilder {

    public MutableTokenBuilder(T token) {
        super(token);
    }

    public B withDirection(Direction dir) {
        this.token.setDirection(dir);
        return (B) this;
    }
    public B withMutability(boolean movable, boolean turnable) {
        this.token.setMovable(movable);
        this.token.setTurnable(turnable);
        return (B) this;
    }
}
