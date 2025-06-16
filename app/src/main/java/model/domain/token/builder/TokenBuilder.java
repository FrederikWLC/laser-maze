package model.domain.token.builder;

import model.domain.board.Direction;
import model.domain.board.Position;
import model.domain.token.*;

import java.util.function.Supplier;

public class TokenBuilder<T extends Token> {

    private final Supplier<T> factory;
    private Position position;
    private Direction direction;
    private Boolean movable;
    private Boolean turnable;
    private boolean requiredTarget = false;

    private TokenBuilder(Supplier<T> factory) {
        this.factory = factory;
    }
    public static <T extends Token> TokenBuilder<T> of(Supplier<T> factory) {
        return new TokenBuilder<>(factory);
    }

    public TokenBuilder<T> withPosition(int x, int y) {
        this.position = new Position(x, y);
        return this;
    }

    public TokenBuilder<T> withDirection(Direction dir) {
        this.direction = dir;
        return this;
    }
    public TokenBuilder<T> withMutability(boolean movable, boolean turnable) {
        if (factory.get() instanceof CellBlockerToken) {
            throw new IllegalStateException("CellBlockerToken cannot be mutable.");
        }
        this.movable = movable;
        this.turnable = turnable;
        return this;
    }

    public TokenBuilder<T> withRequiredTarget() {
        this.requiredTarget = true;
        return this;
    }

    public T build() {
        T token = factory.get();
        if (token instanceof TargetMirrorToken) {
            ((ITargetToken) token).setRequiredTarget(requiredTarget);
        } else if (requiredTarget) {
            throw new IllegalStateException("Only ITargetToken can be marked as a target.");
        }
        if (!(token instanceof IMutableToken)) {}
        else if (movable == null || turnable == null) {
            throw new IllegalStateException("Mutability must be set before building the token.");
        }
        else {
            ((ITurnableToken) token).setDirection(direction);
            ((IMovableToken) token).setMovable(movable);
            ((ITurnableToken) token).setTurnable(turnable);
        }
        token.setPosition(position);

        return ((T) token);
    }
}