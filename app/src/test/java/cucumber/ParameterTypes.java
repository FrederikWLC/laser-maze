package cucumber;

import io.cucumber.java.ParameterType;
import model.domain.board.Direction;
import model.domain.token.base.Token;
import model.domain.token.builder.base.TokenBuilder;
import model.domain.token.builder.impl.*;
import model.domain.token.impl.*;

public class ParameterTypes extends BaseSteps {
    @ParameterType("(?i)up|down|left|right|north|south|east|west")
    public Direction direction(String dir) {
        return Direction.valueOf(dir.toUpperCase());
    }

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint")
    public String tokenName(String name) {
        return name.toLowerCase();
    }
}
