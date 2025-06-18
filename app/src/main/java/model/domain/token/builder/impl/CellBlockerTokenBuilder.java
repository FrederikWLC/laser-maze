package model.domain.token.builder.impl;

import model.domain.token.builder.base.ITokenBuilder;
import model.domain.token.builder.base.TokenBuilder;
import model.domain.token.impl.CellBlockerToken;

public class CellBlockerTokenBuilder extends TokenBuilder<CellBlockerTokenBuilder,CellBlockerToken> implements ITokenBuilder {
    public CellBlockerTokenBuilder() {
        super(new CellBlockerToken());
    }
}
