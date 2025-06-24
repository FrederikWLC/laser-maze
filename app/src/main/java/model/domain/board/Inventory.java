package model.domain.board;

import model.domain.token.base.Token;

import java.util.HashMap;
import java.util.Map;

public class Inventory extends TileContainer {

    public Inventory() {
        this.width = 5;
        this.height = 1;
        this.tiles = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            tiles[x][0] = new Tile(x, 0);
        }
    }

    public Map<String, Integer> getAvailableTokens() {
        Map<String, Integer> tokenCounts = new HashMap<>();
        for (int x = 0; x < width; x++) {
            Token token = tiles[x][0].getToken();
            if (token != null) {
                String name = token.getClass().getSimpleName();
                tokenCounts.put(name, tokenCounts.getOrDefault(name, 0) + 1);
            }
        }
        return tokenCounts;
    }

    public Token getTokenByType(String tokenTypeName) {
        String normalized = tokenTypeName.trim().toLowerCase();
        for (int x = 0; x < width; x++) {
            Token token = tiles[x][0].getToken();
            if (token != null) {
                String name = token.getClass().getSimpleName().toLowerCase();
                if (name.contains(normalized.replace(" ", ""))) {
                    return token;
                }
            }
        }
        return null;
    }

    public int getTotalTokenCount() {
        int count = 0;
        for (int x = 0; x < width; x++) {
            if (!tiles[x][0].isEmpty()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void addToken(Token token) {
        for (int x = 0; x < width; x++) {
            if (tiles[x][0].isEmpty()) {
                tiles[x][0].setToken(token);
                token.setPosition(new Position(x, 0));
                return;
            }
        }
        throw new IllegalStateException("Inventory is full");
    }

    public Token getTokenAtPosition(int index) {
        if (index >= 0 && index < width) {
            return tiles[index][0].getToken();
        }
        return null;
    }

    public void removeToken(Token token) {
        for (int x = 0; x < width; x++) {
            Token current = tiles[x][0].getToken();
            if (current != null && current.equals(token)) {
                tiles[x][0].setToken(null);
                return;
            }
        }
    }

    public int countTokensByType(String typeName) {
        String normalized = typeName.trim().toLowerCase().replace(" ", "");
        int count = 0;
        for (int x = 0; x < width; x++) {
            Token t = tiles[x][0].getToken();
            if (t != null && t.getClass().getSimpleName().toLowerCase().contains(normalized)) {
                count++;
            }
        }
        return count;
    }


}
