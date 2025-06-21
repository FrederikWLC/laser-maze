package model.domain.token.builder.impl;

import model.domain.token.base.ITargetToken;
import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.builder.base.ITargetBuilder;
import model.domain.token.impl.TargetMirrorToken;

public class TargetMirrorTokenBuilder extends MutableTokenBuilder<TargetMirrorTokenBuilder,TargetMirrorToken> implements ITargetBuilder {

    public TargetMirrorTokenBuilder() {
        super(new TargetMirrorToken());
    }

    public TargetMirrorTokenBuilder withRequiredTarget() {
        return this.withRequiredTarget(true);
    }
    public TargetMirrorTokenBuilder withRequiredTarget(boolean isRequiredTarget) {
        ((ITargetToken) this.token).setRequiredTarget(true);
        return this;
    }

}
