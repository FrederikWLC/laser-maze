package model.domain.token.builder.impl;

import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.impl.LaserToken;

public class LaserTokenBuilder extends MutableTokenBuilder<LaserTokenBuilder,LaserToken> {

    public LaserTokenBuilder() {
        super(new LaserToken());
    }
}