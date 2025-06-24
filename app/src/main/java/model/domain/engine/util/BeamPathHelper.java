package model.domain.engine.util;

import model.domain.board.PositionTurn;

import java.util.List;
import java.util.stream.Stream;

public class BeamPathHelper {

    public List<PositionTurn> addToBeamPath(List<PositionTurn> path,PositionTurn positionTurn) {
        return Stream.concat(path.stream(), Stream.of(positionTurn)).toList();
    }

    public List<PositionTurn> addAllBeamPaths(List<List<PositionTurn>> allPaths) {
        return allPaths.stream()
                .flatMap(List::stream)
                .toList();
    }

}
