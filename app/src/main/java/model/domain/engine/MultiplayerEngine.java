package model.domain.engine;

import model.domain.level.Level;
import model.domain.multiplayer.Multiplayer;
import model.persistence.serializer.LevelSerializer;

public class MultiplayerEngine {

    Multiplayer multiplayer;
    LevelSerializer levelSerializer = new LevelSerializer();

    public MultiplayerEngine(Multiplayer multiplayer) {
        // Initialize multiplayer engine
        this.multiplayer = multiplayer;
    }

    private void restartLevelState(Multiplayer multiplayer) {
        Level defaultLevel = multiplayer.getDefaultLevel();
        Level defaultLevelClone = levelSerializer.clone(defaultLevel);
        multiplayer.setCurrentLevel(defaultLevelClone);
    }

    public void startTurn(Multiplayer multiplayer, long startTime) {
        if (multiplayer.isTurnActive()) {
            throw new IllegalStateException("Cannot start a new turn while the current turn is still active.");
        }
        nextTurn(multiplayer); // Move to the next player automatically when starting a turn
        restartLevelState(multiplayer);
        multiplayer.setPlayerStartStamp(startTime);
        multiplayer.setTurnActive(true);
    }

    public void endTurn(Multiplayer multiplayer, long completionTime) {
        if (!multiplayer.isTurnActive()) {
            throw new IllegalStateException("Cannot end a turn that is not active.");
        }
        multiplayer.setPlayerEndStamp(completionTime);
        multiplayer.setTurnActive(false);
    }

    private void nextTurn(Multiplayer multiplayer) {
        multiplayer.incrementCurrentPlayerIndex();
    }
}
