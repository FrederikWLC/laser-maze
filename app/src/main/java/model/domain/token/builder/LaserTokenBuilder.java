package model.domain.token.builder;

import model.domain.token.LaserToken;

public class LaserTokenBuilder extends MutableTokenBuilder<LaserTokenBuilder,LaserToken> {

    public LaserTokenBuilder() {
        super(new LaserToken());
    }
}