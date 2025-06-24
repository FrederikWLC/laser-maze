package model.domain.token.base;

import model.domain.board.PositionTurn;

public interface ICheckpointToken {
    boolean isChecked(PositionTurn beamPositionTurn);
}
