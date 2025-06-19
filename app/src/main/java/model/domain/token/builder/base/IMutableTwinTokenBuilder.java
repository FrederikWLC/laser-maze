package model.domain.token.builder.base;

import model.domain.board.Direction;
import model.domain.token.base.Token;

public interface IMutableTwinTokenBuilder {
    MutableTwinTokenBuilder withPositionA(int x, int y);
    MutableTwinTokenBuilder withPositionB(int x, int y);
    MutableTwinTokenBuilder withDirectionA(Direction dir);
    MutableTwinTokenBuilder withDirectionB(Direction dir);
    MutableTwinTokenBuilder withMutabilityA(boolean movable, boolean turnable);
    MutableTwinTokenBuilder withMutabilityB(boolean movable, boolean turnable);
    Token[] build();
}
