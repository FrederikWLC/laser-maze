package model.domain.engine;

import model.domain.token.base.MutableTwinToken;
import model.domain.token.base.Token;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TokenInitializerEngine {
    public void init(List<Token> tokens) {
        linkTwins(tokens);
        // later: configure other future initialization logic for tokens
        // Violates the Single Responsibility Principle, but that's okay for now
    }

    public void linkTwins(List<Token> tokens) {
        // Pull out only the mutable-twin tokens
        List<MutableTwinToken> allTwins = tokens.stream()
                .filter(token -> token instanceof MutableTwinToken)
                .map(token -> (MutableTwinToken) token)
                .toList();

        // Group by their actual subclasses
        Map<Class<? extends MutableTwinToken>, List<MutableTwinToken>> byType =
                allTwins.stream()
                        .collect(Collectors.groupingBy(MutableTwinToken::getClass));

        // For each subtype group of exactly two, link them
        for (var entry : byType.entrySet()) {
            List<MutableTwinToken> pair = entry.getValue();
            if (pair.size() == 2) { // Only link if there are exactly two tokens of the same type
                pair.get(0).setTwin(pair.get(1));
                pair.get(1).setTwin(pair.get(0));
            }
        }
    }
}

