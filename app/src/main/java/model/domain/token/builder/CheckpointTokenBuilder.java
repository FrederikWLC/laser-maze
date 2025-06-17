package model.domain.token.builder;
import model.domain.token.CheckpointToken;

public class CheckpointTokenBuilder extends MutableTokenBuilder<CheckpointTokenBuilder,CheckpointToken> {

    public CheckpointTokenBuilder() {
        super(new CheckpointToken());
    }
}