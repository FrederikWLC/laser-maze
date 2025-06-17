package model.domain.token.builder;
import model.domain.board.Direction;

public interface IMutableTokenBuilder extends ITokenBuilder {
    IMutableTokenBuilder withDirection(Direction dir);
    IMutableTokenBuilder withMutability(boolean movable, boolean turnable);
}
