package model.domain.token.builder.impl;
import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.impl.CheckpointToken;

public class CheckpointTokenBuilder extends MutableTokenBuilder<CheckpointTokenBuilder,CheckpointToken> {

    public CheckpointTokenBuilder() {
        super(new CheckpointToken());
    }
}