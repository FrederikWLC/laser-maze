package cucumber;

import io.cucumber.java.ParameterType;
import model.Direction;
import model.Token;

public class ParameterTypes {
    @ParameterType("(?i)up|down|left|right|north|south|east|west")
    public Direction direction(String dir) {
        return Direction.valueOf(dir.toUpperCase());
    }

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint")
    public String tokenName(String name) {
        return name.toLowerCase();
    }
}
