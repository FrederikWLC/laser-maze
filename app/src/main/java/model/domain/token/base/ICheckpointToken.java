package model.domain.token.base;

import model.domain.board.PositionDirection;

public interface ICheckpointToken {
    boolean isPenetrated(PositionDirection beamPositionDirection);
}
