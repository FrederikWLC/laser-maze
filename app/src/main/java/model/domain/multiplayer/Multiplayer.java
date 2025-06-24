package model.domain.multiplayer;

import model.domain.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Multiplayer {
    private final int playerCount;
    private Integer currentPlayerIndex;
    private boolean turnIsActive;
    private final Long[] playerStartStamps;
    private final Long[] playerEndStamps;
    private final Level defaultLevel;
    private Level[] playerLevels;

    public Multiplayer(Level defaultLevel, int playerCount) {
        this.defaultLevel = defaultLevel;
        this.playerCount = playerCount;
        this.playerStartStamps = new Long[playerCount];
        this.playerEndStamps = new Long[playerCount];
        this.playerLevels = new Level[playerCount];
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public Long[] getPlayerStartStamps() {
        return playerStartStamps;
    }

    public void setPlayerStartStamp(Long startTime) {
        this.playerStartStamps[getCurrentPlayerIndex()] = startTime;
    }

    public Long[] getPlayerEndStamps() {
        return playerEndStamps;
    }

    public void setPlayerEndStamp(Long completionTime) {
        this.playerEndStamps[getCurrentPlayerIndex()] = completionTime;
    }

    public Integer getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void incrementCurrentPlayerIndex() {
        if (getCurrentPlayerIndex() == null) {
            currentPlayerIndex = 0;
        } else {
            if (getPlayerCount() == getCurrentPlayerIndex() + 1) {
                throw new IllegalStateException("Cannot increment max player index: " + getCurrentPlayerIndex());
            } else {
                currentPlayerIndex++;
            }
        }
    }

    public Integer getPlayerTime(int playerIndex) {
        return getPlayerTimeByEndStamp(playerIndex,getPlayerEndStamps()[playerIndex]);
    }

    public Integer getPlayerTimeByEndStamp(int playerIndex, Long endStamp) {
        if (playerIndex < 0 || playerIndex >= playerCount || endStamp == null) {
            return null;
        }
        Long startStamp = getPlayerStartStamps()[playerIndex];
        if (startStamp == null) return null;

        int time = (int) (endStamp - startStamp);
        return time > 0 ? time : null;
    }

    public List<Integer> getPlayerTimes() {
        return IntStream.range(0, getPlayerCount())
                .mapToObj(this::getPlayerTime)
                .collect(Collectors.toList());
    }

    public List<PlayerScore> getSortedPlayerScoreTimes() {
        return IntStream.range(0, playerCount)
                .mapToObj(this::getPlayerScoreTime)
                .sorted(Comparator.comparing(
                        PlayerScore::timeMillis,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .toList();
    }

    // Only returns time if player's level is complete, otherwise returns null
    public PlayerScore getPlayerScoreTime(int i) {
        return new PlayerScore(i + 1, getPlayerLevel(i).isComplete() ? getPlayerTime(i) : null);
    }

    public Level getCurrentLevel() {
        return getPlayerLevel(getCurrentPlayerIndex());
    }

    public Level getPlayerLevel(int i) {
        return playerLevels[i];
    }

    public void setCurrentLevel(Level level) {
        playerLevels[getCurrentPlayerIndex()] = level;
    }

    public Level getDefaultLevel() {
        return defaultLevel;
    }

    public boolean isTurnActive() {
        return turnIsActive;
    }

    public void setTurnActive(boolean active) {
        turnIsActive = active;
    }

    public boolean allTurnsPlayed() {
        return getPlayerTimes().stream().allMatch(Objects::nonNull);
    }
}
