package model.domain.token;

import model.domain.board.PositionDirection;

public interface ICheckpointToken {
    boolean isPenetrated(PositionDirection beamPositionDirection);
}
