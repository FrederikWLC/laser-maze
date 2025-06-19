// BaseSteps.java
package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import model.domain.board.*;
import model.domain.engine.BoardEngine;
import model.domain.level.Level;
import model.domain.token.base.*;
import model.domain.token.builder.base.*;
import model.domain.token.builder.impl.*;
import model.domain.token.impl.*;

import java.util.List;

public abstract class BaseSteps {
    public Level level;
    public Board board;
    public Inventory inventory;
    public Tile tile;
    public Token token;
    public LaserToken laser;
    public CellBlockerToken cellBlocker;
    public DoubleMirrorToken doubleMirror;
    public TargetMirrorToken targetMirror;
    public BeamSplitterToken beamSplitter;
    public CheckpointToken checkpoint;
    public PortalToken portal;


    public TokenBuilder tokenBuilder(String name) {
        return switch (name.toLowerCase()) {
            case "laser", "lasertoken" -> new LaserTokenBuilder();
            case "cell blocker", "cellblockertoken" -> new CellBlockerTokenBuilder();
            case "double mirror", "doublemirrortoken" -> new DoubleMirrorTokenBuilder();
            case "target mirror", "targetmirrortoken" -> new TargetMirrorTokenBuilder();
            case "beam splitter", "beamsplittertoken" -> new BeamSplitterTokenBuilder();
            case "checkpoint", "checkpointtoken" -> new CheckpointTokenBuilder();
            case "portal", "portaltoken" -> new PortalTokenBuilder();
            default -> throw new IllegalArgumentException("Unknown token: " + name);
        };
    }

    public Class<? extends Token> tokenType(String name) {
        return switch (name.toLowerCase()) {
            case "laser", "lasertoken" -> LaserToken.class;
            case "cell blocker", "cellblockertoken" -> CellBlockerToken.class;
            case "double mirror", "doublemirrortoken" -> DoubleMirrorToken.class;
            case "target mirror", "targetmirrortoken" -> TargetMirrorToken.class;
            case "beam splitter", "beamsplittertoken" -> BeamSplitterToken.class;
            case "checkpoint", "checkpointtoken" -> CheckpointToken.class;
            case "portal", "portaltoken" -> PortalToken.class;
            default -> throw new IllegalArgumentException("Unknown token type: " + name);
        };
    }

    public Token buildToken(String tokenName, Integer x, Integer y, Direction direction, boolean movable, boolean turnable) {
        TokenBuilder builder = tokenBuilder(tokenName);
        if (IMutableToken.class.isAssignableFrom(tokenType(tokenName))) {
            builder = ((MutableTokenBuilder) builder).withMutability(movable, turnable).withDirection(direction);
        }
        if (x != null && y != null) {
            builder = builder.withPosition(x, y);
        }
        return builder.build();
    }

    public void saveTokenAsType(Token token) {
        if (token instanceof LaserToken t) laser = t;
        else if (token instanceof CellBlockerToken t) cellBlocker = t;
        else if (token instanceof DoubleMirrorToken t) doubleMirror = t;
        else if (token instanceof TargetMirrorToken t) targetMirror = t;
        else if (token instanceof BeamSplitterToken t) beamSplitter = t;
        else if (token instanceof CheckpointToken t) checkpoint = t;
        else if (token instanceof PortalToken t) portal = t;
        else throw new IllegalArgumentException("Unknown token class: " + token.getClass());
    }

    public List<Token> getTokensFromTable(DataTable table) {
        return table.asMaps(String.class, String.class).stream()
                .map(row -> {
                    boolean turnable = Boolean.parseBoolean(row.get("turnable"));
                    boolean movable = Boolean.parseBoolean(row.get("movable"));
                    Integer x = row.get("x") != null ? Integer.parseInt(row.get("x")) : null;
                    Integer y = row.get("y") != null ? Integer.parseInt(row.get("y")) : null;
                    Direction dir = row.get("dir") != null ? Direction.valueOf(row.get("dir").toUpperCase()) : null;
                    return buildToken(row.get("token"), x, y, dir, movable, turnable);
                })
                .toList();
    }

    public void placeTokenOnTheBoard(Board board, String tokenName, int x, int y, Direction direction, boolean movable, boolean turnable) {
        token = buildToken(tokenName, x, y, direction, movable, turnable);
        saveTokenAsType(token);
        BoardEngine.placeToken(board,token, new Position(x, y)) ;
    }


    public List<MutableTwinToken> getTwinPairOfType(Class<? extends MutableTwinToken> tokenType,List<Token> tokens) {
        List<MutableTwinToken> pair = tokens.stream()
                .filter(tokenType::isInstance)
                .map(t -> (MutableTwinToken)t)
                .limit(2)
                .toList();

        if (pair.size() < 2) {
            throw new IllegalStateException(
                    "Expected at least two " + tokenType.getSimpleName() + "s, but found " + pair.size()
            );
        }

        return pair;
    }

    protected Direction direction(String dir) {
        return switch (dir.trim().toUpperCase()) {
            case "UP", "NORTH"    -> Direction.UP;
            case "DOWN", "SOUTH"  -> Direction.DOWN;
            case "LEFT", "WEST"   -> Direction.LEFT;
            case "RIGHT", "EAST"  -> Direction.RIGHT;
            default -> throw new IllegalArgumentException("Invalid direction string: " + dir);
        };
    }


}
