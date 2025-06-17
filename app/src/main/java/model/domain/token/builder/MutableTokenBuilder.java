package model.domain.token.builder;

import model.domain.board.Direction;
import model.domain.token.CellBlockerToken;
import model.domain.token.IMutableToken;
import model.domain.token.MutableToken;

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
