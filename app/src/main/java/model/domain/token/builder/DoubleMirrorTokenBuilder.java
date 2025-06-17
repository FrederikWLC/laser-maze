package model.domain.token.builder;

import model.domain.token.DoubleMirrorToken;

public class DoubleMirrorTokenBuilder extends MutableTokenBuilder<DoubleMirrorTokenBuilder,DoubleMirrorToken>  {

    public DoubleMirrorTokenBuilder() {
        super(new DoubleMirrorToken());
    }
}
