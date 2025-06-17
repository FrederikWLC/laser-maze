package model.domain.token.builder;

import model.domain.token.ITargetToken;
import model.domain.token.TargetMirrorToken;

public class TargetMirrorTokenBuilder extends MutableTokenBuilder<TargetMirrorTokenBuilder,TargetMirrorToken> implements ITargetBuilder {

    public TargetMirrorTokenBuilder() {
        super(new TargetMirrorToken());
    }

    public TargetMirrorTokenBuilder withRequiredTarget() {
        ((ITargetToken) this.token).setRequiredTarget(true);
        return this;
    }

}
