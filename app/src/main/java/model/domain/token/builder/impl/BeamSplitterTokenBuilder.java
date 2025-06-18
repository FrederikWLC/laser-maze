package model.domain.token.builder.impl;

import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.impl.BeamSplitterToken;

public class BeamSplitterTokenBuilder extends MutableTokenBuilder<BeamSplitterTokenBuilder,BeamSplitterToken> {

    public BeamSplitterTokenBuilder() {
        super(new BeamSplitterToken());
    }
}
