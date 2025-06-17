package model.domain.token.builder;

import model.domain.token.BeamSplitterToken;

public class BeamSplitterTokenBuilder extends MutableTokenBuilder<BeamSplitterTokenBuilder,BeamSplitterToken>{

    public BeamSplitterTokenBuilder() {
        super(new BeamSplitterToken());
    }
}
