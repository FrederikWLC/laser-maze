package model.domain.token.builder.base;

public interface ITargetBuilder extends ITokenBuilder {
    ITargetBuilder withRequiredTarget(boolean isRequiredTarget);
}
