package model.domain.token.builder.impl;

import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.impl.PortalToken;

public class PortalTokenBuilder extends MutableTokenBuilder<PortalTokenBuilder, PortalToken> {

    public PortalTokenBuilder() {
        super(new PortalToken());
    }
}
