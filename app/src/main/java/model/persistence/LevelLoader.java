package model.persistence;

import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        List<Token> tokens = Stream.concat(requiredTokens.stream(),preplacedTokens.stream()).toList();
        int targetNumberRequired = LevelStorage.getRequiredTargetNumberFor(id);

        return new LevelBuilder(id)
                .withTokens(tokens)
                .withRequiredTargetNumber(targetNumberRequired)
                .build();
    }
}
