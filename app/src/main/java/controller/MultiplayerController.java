package controller;

import model.domain.engine.MultiplayerEngine;
import model.domain.level.Level;
import model.domain.multiplayer.Multiplayer;
import view.GamePanel;

public class MultiplayerController {

    private final Multiplayer multiplayer;
    private final MultiplayerEngine multiplayerEngine;
    private final LevelController levelController;
    private final GamePanel gamePanel;

    public MultiplayerController(Multiplayer multiplayer, LevelController levelController, GamePanel gamePanel) {
        this.multiplayer = multiplayer;
        this.multiplayerEngine = new MultiplayerEngine();
        this.levelController = levelController;
        this.gamePanel = gamePanel;
    }

    public void startGame() {
        multiplayerEngine.startTurn(multiplayer, System.currentTimeMillis());
        levelController.setCurrentLevel(multiplayer.getCurrentLevel());
        levelController.reloadLevelUI();
    }

    public void onLevelComplete() {
        multiplayerEngine.endTurn(multiplayer, System.currentTimeMillis());

        if (!multiplayer.allTurnsPlayed()) {
            multiplayerEngine.startTurn(multiplayer, System.currentTimeMillis());
            levelController.setCurrentLevel(multiplayer.getCurrentLevel());
            levelController.reloadLevelUI();
        } else {
            showScoreboard();
        }
    }

    private void showScoreboard() {
        StringBuilder message = new StringBuilder("Multiplayer Scoreboard:\n");
        for (var score : multiplayer.getSortedPlayerScoreTimes()) {
            String timeStr = (score.timeMillis() != null) ? score.timeMillis() + " ms" : "DNF";
            message.append("Player ").append(score.playerNumber()).append(": ").append(timeStr).append("\n");
        }

        javax.swing.JOptionPane.showMessageDialog(
                gamePanel,
                message.toString(),
                "Scoreboard",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        levelController.exitLevel();
    }
}
