package model.domain.token.builder.base;
import model.domain.token.base.Token;

public interface ITokenBuilder {
    TokenBuilder withPosition(int x, int y);
    Token build();
}
