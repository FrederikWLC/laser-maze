package model.domain.engine;

import model.domain.board.Inventory;
import model.domain.board.Position;
import model.domain.token.base.Token;

public class InventoryEngine {

    public static void addToken(Inventory inventory, Token token) {
        inventory.addToken(token); // This now adds to the next available tile
    }

    public static void removeToken(Inventory inventory, Token token) {
        for (int x = 0; x < 5; x++) {
            if (inventory.getTile(x, 0).getToken() == token) {
                inventory.getTile(x, 0).setToken(null);
                return;
            }
        }
    }

    public static Token getTokenAtSlot(Inventory inventory, int slot) {
        return inventory.getTokenAtPosition(slot);
    }

    public static boolean isFull(Inventory inventory) {
        for (int x = 0; x < 5; x++) {
            if (inventory.getTile(x, 0).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static int getFirstEmptySlot(Inventory inventory) {
        for (int x = 0; x < 5; x++) {
            if (inventory.getTile(x, 0).isEmpty()) {
                return x;
            }
        }
        return -1; // Inventory is full
    }

    public static void placeTokenAt(Inventory inventory, Token token, int slotIndex) {
        if (slotIndex >= 0 && slotIndex < 5) {
            inventory.getTile(slotIndex, 0).setToken(token);
            token.setPosition(new Position(slotIndex, 0));
        }
    }
}
