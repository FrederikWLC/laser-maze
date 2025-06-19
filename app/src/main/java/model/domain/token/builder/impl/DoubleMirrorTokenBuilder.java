package model.domain.token.builder.impl;

import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.impl.DoubleMirrorToken;

public class DoubleMirrorTokenBuilder extends MutableTokenBuilder<DoubleMirrorTokenBuilder,DoubleMirrorToken> {

    public DoubleMirrorTokenBuilder() {
        super(new DoubleMirrorToken());
    }
}
