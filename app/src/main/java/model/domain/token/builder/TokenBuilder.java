package model.domain.token.builder;

import model.domain.board.Position;
import model.domain.token.*;

public abstract class TokenBuilder<B extends TokenBuilder<B,T>, T extends Token> implements ITokenBuilder {

    protected T token;

    public TokenBuilder(T token) {
        this.token = token;
    }

    public B withPosition(int x, int y) {
        this.token.setPosition(new Position(x, y));
        return (B) this;
    }

    public T build() {
        return token;
    }

}