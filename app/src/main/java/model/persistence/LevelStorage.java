package model.persistence;

import model.domain.board.Direction;
import model.domain.token.base.Token;
import model.domain.token.builder.impl.*;

import java.util.List;
import java.util.stream.Stream;

public class LevelStorage {

    public static List<Token> getPreplacedTokensFor(int levelId) {
        // Logic to retrieve preplaced tokens for the given level
        switch (levelId) {
            case 1:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,false).withPosition(1,1).withDirection(Direction.DOWN).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(3,3).withDirection(Direction.LEFT).withRequiredTarget().build()
                );
            case 2:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,true).withPosition(0,4).withDirection(Direction.DOWN).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(0,3).withDirection(Direction.LEFT).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(4,0).withDirection(Direction.DOWN).withRequiredTarget().build()
                );
            case 3:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(0,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(0,4).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(1,4).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(4,3).withDirection(Direction.UP).build()
                );
            case 4:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,false).withPosition(1,0).withDirection(Direction.DOWN).build(),
                        new CheckpointTokenBuilder().withMutability(false,false).withPosition(0,1).withDirection(Direction.DOWN).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(0,4).build()
                );
            case 17:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,true).withPosition(0,0).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(4,0).withRequiredTarget().build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,false).withPosition(3,1).withDirection(Direction.LEFT).build()
                );
            case 18:
                return List.of(
                        new CheckpointTokenBuilder().withMutability(false,true).withPosition(2,1).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(2,4).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(1,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(1,4).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(3,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(3,4).build()
                        );
            case 28:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(1,2).withDirection(Direction.RIGHT).build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,false).withPosition(2,2).withDirection(Direction.RIGHT).build(),
                        new CellBlockerTokenBuilder().withPosition(3,1).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(3,3).withDirection(Direction.RIGHT).build(),
                        new LaserTokenBuilder().withMutability(false,true).withPosition(4,2).withDirection(Direction.DOWN).build()
                );
            case 30:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(3,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(3,1).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(1,1).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(2,0).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(1,3).withRequiredTarget().build(),
                        new CheckpointTokenBuilder().withMutability(false,true).withPosition(1,2).build(),
                        new BeamSplitterTokenBuilder().withMutability(false,false).withPosition(2,3).withDirection(Direction.RIGHT).build()
                        );
            case 33:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,true).withPosition(0,3).build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,true).withPosition(2,1).build(),
                        new CellBlockerTokenBuilder().withPosition(2,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(4,1).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(3,4).withRequiredTarget().build()
                        );
            case 34:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,false).withPosition(2,0).withDirection(Direction.DOWN).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(3,1).withDirection(Direction.LEFT).withRequiredTarget().build(),
                        new CheckpointTokenBuilder().withMutability(false,true).withPosition(3,2).build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,false).withPosition(0,3).withDirection(Direction.DOWN).build()
                );
            case 36:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,true).withPosition(3,1).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(2,0).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(0,1).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(1,2).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(0,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(2,3).build(),
                        new CheckpointTokenBuilder().withMutability(false,true).withPosition(1,1).build(),
                        new BeamSplitterTokenBuilder().withMutability(false,true).withPosition(2,2).build()
                        );
            case 40:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,true).withPosition(0,0).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(4,3).withDirection(Direction.LEFT).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(3,4).withDirection(Direction.UP).withRequiredTarget().build(),
                        new CheckpointTokenBuilder().withMutability(false,false).withPosition(2,1).withDirection(Direction.UP).build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,false).withPosition(1,2).withDirection(Direction.UP).build()
                        );
            case 52:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,false).withPosition(4,2).withDirection(Direction.LEFT).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(4,1).build(),
                        new BeamSplitterTokenBuilder().withMutability(false,true).withPosition(3,1).build(),
                        new BeamSplitterTokenBuilder().withMutability(false,true).withPosition(1,4).build()
                );
            case 54:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(1,3).withDirection(Direction.UP).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(2,2).withDirection(Direction.DOWN).withRequiredTarget().build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(4,0).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(3,4).build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,true).withPosition(3,1).build(),
                        new BeamSplitterTokenBuilder().withMutability(false,true).withPosition(1,0).build()
                        );

            case 58:
                return List.of(
                        new LaserTokenBuilder().withMutability(false,true).withPosition(2,3).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(0,2).withDirection(Direction.DOWN).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,false).withPosition(0,4).withDirection(Direction.DOWN).build(),
                        new TargetMirrorTokenBuilder().withMutability(false,true).withPosition(0,3).build(),
                        new CheckpointTokenBuilder().withMutability(false,true).withPosition(2,1).build(),
                        new DoubleMirrorTokenBuilder().withMutability(false,false).withPosition(4,4).withDirection(Direction.RIGHT).build()
                );
            default: return List.of();

        }
    }

    public static List<Token> getRequiredTokensFor(int levelId) {
        // Logic to retrieve required tokens for the given level
        switch (levelId) {
            case 1:
                return List.of(
                        new DoubleMirrorTokenBuilder().withMutability(true,true).build()
                );
            case 2:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build()
                );
            case 3:
                return List.of(
                        new LaserTokenBuilder().withMutability(true,true).build()
                );
            case 4:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build()
                );
            case 17:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).withRequiredTarget().build(),
                        new BeamSplitterTokenBuilder().withMutability(true,true).build()
                );
            case 18:
                return List.of(
                        new LaserTokenBuilder().withMutability(true,true).build()
                );
            case 28:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new CheckpointTokenBuilder().withMutability(true,true).build()
                );
            case 30:
                return List.of(
                        new LaserTokenBuilder().withMutability(true,true).build()
                );
            case 33:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new BeamSplitterTokenBuilder().withMutability(true,true).build()
                );
            case 34:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build()
                );
            case 36:
                return List.of(
                        new DoubleMirrorTokenBuilder().withMutability(true,true).build()
                );
            case 40:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new BeamSplitterTokenBuilder().withMutability(true,true).build()
                );
            case 52:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build()
                );
            case 54:
                return List.of(
                        new LaserTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new BeamSplitterTokenBuilder().withMutability(true,true).build()
                );
            case 58:
                return List.of(
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new TargetMirrorTokenBuilder().withMutability(true,true).build(),
                        new BeamSplitterTokenBuilder().withMutability(true,true).build()
                );
            default: return List.of();
        }
    }

    public static List<Token> getTokensFor(int levelId) {
        return Stream.concat(getRequiredTokensFor(levelId).stream(), getPreplacedTokensFor(levelId).stream())
                .toList();
    }

        public static int getRequiredTargetNumberFor(int levelId) {
            // Logic to retrieve the number of targets required for the given level
            switch (levelId) {
                case 1:
                    return 1;
                case 2:
                    return 1;
                case 3:
                    return 1;
                case 4:
                    return 1;
                case 17:
                    return 2;
                case 18:
                    return 1;
                case 28:
                    return 1;
                case 30:
                    return 2;
                case 33:
                    return 2;
                case 34:
                    return 1;
                case 36:
                    return 2;
                case 40:
                    return 2;
                case 52:
                    return 3;
                case 54:
                    return 3;
                case 58:
                    return 2;
                default:
                    return 1;
            }

        }
}
