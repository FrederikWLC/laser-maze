package model.domain.token.base;

public interface ITargetToken extends IToken {

    boolean isRequiredTarget();

    void setRequiredTarget(boolean requiredTarget);

}
