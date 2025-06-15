package model;

public interface ILaserToken {
    boolean isActive();
    void trigger(boolean isActive);
}
