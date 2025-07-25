package model.domain.token.base;

public interface ILaserToken {
    boolean isActive();
    boolean isTriggerable(); // is only triggerable when placed and turned
    void trigger(boolean isActive);
}
