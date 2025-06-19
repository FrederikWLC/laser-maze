package model.domain.token.builder.impl;

import model.domain.token.builder.base.MutableTwinTokenBuilder;
import model.domain.token.impl.PortalToken;

public class PortalTwinTokenBuilder extends MutableTwinTokenBuilder<PortalTwinTokenBuilder, PortalToken> {
    public PortalTwinTokenBuilder() {
        super(new PortalToken(), new PortalToken());
    }
}
