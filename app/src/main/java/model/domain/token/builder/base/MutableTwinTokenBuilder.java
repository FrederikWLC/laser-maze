package model.domain.token.builder.base;

import model.domain.board.Direction;
import model.domain.board.Position;
import model.domain.token.base.MutableTwinToken;
import model.domain.token.base.Token;

public class MutableTwinTokenBuilder<B extends MutableTwinTokenBuilder<B,T>, T extends MutableTwinToken> implements IMutableTwinTokenBuilder {

    protected T tokenA;
    protected T tokenB;

    public MutableTwinTokenBuilder(T tokenA, T tokenB) {
        this.tokenA = tokenA;
        this.tokenA = tokenB;
    }a

    public B withPositionA(int x, int y) {
        this.tokenA.setPosition(new Position(x, y));
        return (B) this;
    }

    public B withPositionB(int x, int y) {
        this.tokenB.setPosition(new Position(x, y));
        return (B) this;

    }

    public B withDirectionA(Direction dir) {
        this.tokenA.setDirection(dir);
        return (B) this;
    }

    public B withDirectionB(Direction dir) {
        this.tokenA.setDirection(dir);
        return (B) this;
    }

    public B withMutabilityA(boolean movable, boolean turnable) {
        this.tokenA.setMovable(movable);
        this.tokenA.setTurnable(turnable);
        return (B) this;
    }

    public B withMutabilityB(boolean movable, boolean turnable) {
        this.tokenA.setMovable(movable);
        this.tokenA.setTurnable(turnable);
        return (B) this;
    }

    public T[] build() {
        return (T[]) new Token[]{ tokenA, tokenB };
    }
}
