package model;

public interface ITargetToken extends IToken {

    boolean isRequiredTarget();

    void setRequiredTarget(boolean requiredTarget);
}
