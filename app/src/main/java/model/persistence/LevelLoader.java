package model.persistence;

import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.Token;

import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static List<Level> loadAll() {
        List<Level> levels = new ArrayList<>();
        for (int i : new int[]{
                1, 2, 3, 4,
                17, 18, 28, 30,
                33, 34, 36, 40,
                52, 54, 58
        }) {
            levels.add(load(i));
        }
        return levels;
    }

    public static Level load(int id) {
        List<Token> preplacedTokens = LevelStorage.getPreplacedTokensFor(id);
        List<Token> requiredTokens = LevelStorage.getRequiredTokensFor(id);
        int targetNumberRequired = LevelStorage.getRequiredTargetNumberFor(id);

        return new LevelBuilder(id)
                .withPreplaced(preplacedTokens)
                .withRequired(requiredTokens)
                .withRequiredTargetNumber(targetNumberRequired)
                .build();
    }
}

