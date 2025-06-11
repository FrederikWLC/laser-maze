package cucumber;

import io.cucumber.java.ParameterType;
import model.Direction;

public class ParameterTypes {
    @ParameterType("(?i)up|down|left|right|north|south|east|west")
    public Direction direction(String dir) {
        return Direction.valueOf(dir.toUpperCase());
    }
}
