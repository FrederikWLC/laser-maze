package model.domain.board.builder;

import model.domain.board.Inventory;
import model.domain.board.Position;
import model.domain.token.base.Token;

import java.util.List;

public class InventoryBuilder {
    public static Inventory buildInventory(List<Token> tokens) {
        Inventory inventory = new Inventory();

        for (int i = 0; i < tokens.size() && i < 5; i++) {
            Token token = tokens.get(i);
            inventory.getTile(i, 0).setToken(token);
            token.setPosition(new Position(i, 0));
        }

        return inventory;
    }
}