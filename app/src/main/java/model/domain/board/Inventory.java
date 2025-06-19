package model.domain.board;

import model.domain.token.base.Token;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory extends TileContainer {
    private final List<Token> backingTokens;

    public Inventory(List<Token> requiredTokens) {
        this.backingTokens = new ArrayList<>(requiredTokens); // create modifiable view
        this.width = 1;
        this.height = backingTokens.size();
        this.tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            tiles[0][y] = new Tile(0, y);
        }
    }

    public Map<String, Integer> getAvailableTokens() {
        return backingTokens.stream()
                .collect(Collectors.groupingBy(t -> t.getClass().getSimpleName(), Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    public Token getTokenByType(String tokenTypeName) {
        String normalizedName = tokenTypeName.trim().toLowerCase();

        for (Token token : backingTokens) {
            String simpleName = token.getClass().getSimpleName().toLowerCase();
            if (simpleName.contains(normalizedName.replace(" ", ""))) {
                return token;
            }
        }
        return null;
    }

    public int getTotalTokenCount() {
        return backingTokens.size();
    }

    public void removeToken(Token token) {
        backingTokens.remove(token);
    }

    public int countTokensByType(String typeName) {
        String normalized = typeName.trim().toLowerCase().replace(" ", "");
        return (int) backingTokens.stream()
                .filter(t -> t.getClass().getSimpleName().toLowerCase().contains(normalized))
                .count();
    }

    @Override
    public void addToken(Token token) {
        backingTokens.add(token);
    }
}
