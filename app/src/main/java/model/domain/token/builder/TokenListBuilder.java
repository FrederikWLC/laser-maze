package model.domain.token.builder;
import model.domain.token.Token;

import java.util.ArrayList;
import java.util.List;

public class TokenListBuilder {
    private final List<Token> tokens = new ArrayList<>();

    public TokenListBuilder add(Token token) {
        this.tokens.add(token);
        return this;
    }

    public TokenListBuilder addAll(List<? extends Token> tokens) {
        this.tokens.addAll(tokens);
        return this;
    }

    public List<Token> build() {
        return List.copyOf(tokens);
    }
}

