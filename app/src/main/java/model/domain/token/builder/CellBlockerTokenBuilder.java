package model.domain.token.builder;

import model.domain.token.CellBlockerToken;

public class CellBlockerTokenBuilder extends TokenBuilder<CellBlockerTokenBuilder,CellBlockerToken> implements ITokenBuilder {
    public CellBlockerTokenBuilder() {
        super(new CellBlockerToken());
    }
}
