package model.domain.token.builder;
import model.domain.token.*;

public interface ITokenBuilder {
    TokenBuilder withPosition(int x, int y);
    Token build();
}
