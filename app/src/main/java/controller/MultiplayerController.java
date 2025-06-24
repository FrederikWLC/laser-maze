package controller;

import model.domain.engine.MultiplayerEngine;
import model.domain.level.Level;
import model.domain.multiplayer.Multiplayer;
import view.GamePanel;

import javax.swing.*;

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

    private void showTurnStartMessage(int playerNumber) {
        JOptionPane.showMessageDialog(
                gamePanel,
                "Player " + playerNumber + ", it's your turn!\nClick OK when ready.",
                "Player " + playerNumber + "'s Turn",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void startGame() {
        startTurn(() -> {
            int playerIndex = multiplayer.getCurrentPlayerIndex();
            showTurnStartMessage(playerIndex + 1); // 1-based display
        });
    }

    private void startTurn(Runnable afterLoadUI) {
        multiplayerEngine.startTurn(multiplayer, System.currentTimeMillis());

        Level freshLevel = multiplayer.getCurrentLevel(); // already set by engine
        levelController.setCurrentLevel(freshLevel);
        levelController.reloadLevelUI();
        gamePanel.startMultiplayerTimer();

        SwingUtilities.invokeLater(afterLoadUI);
    }

    public void onLevelComplete() {
        multiplayerEngine.endTurn(multiplayer, System.currentTimeMillis());
        gamePanel.stopMultiplayerTimer();

        // Check if another turn is available
        if (multiplayer.getCurrentPlayerIndex() < multiplayer.getPlayerCount() - 1) {
            startTurn(() -> {
                int playerIndex = multiplayer.getCurrentPlayerIndex();
                showTurnStartMessage(playerIndex + 1);
            });
        } else {
            showScoreboard();
        }
    }

    private void showScoreboard() {
        StringBuilder message = new StringBuilder("Multiplayer Scoreboard:\n");
        for (var score : multiplayer.getSortedPlayerScoreTimes()) {
            String timeStr = (score.timeMillis() != null)
                    ? score.timeMillis() + " ms"
                    : "DNF";
            message.append("Player ").append(score.playerNumber()).append(": ").append(timeStr).append("\n");
        }

        JOptionPane.showMessageDialog(
                gamePanel,
                message.toString(),
                "Scoreboard",
                JOptionPane.INFORMATION_MESSAGE
        );

        levelController.exitLevel();
    }
}
