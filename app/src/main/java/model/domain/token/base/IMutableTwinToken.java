package model.domain.token.base;

public interface IMutableTwinToken extends IMutableToken {
    void setTwin(MutableTwinToken twin);
    MutableTwinToken getTwin();
}
