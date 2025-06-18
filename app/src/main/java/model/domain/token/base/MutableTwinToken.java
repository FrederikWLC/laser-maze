package model.domain.token.base;

public abstract class MutableTwinToken extends MutableToken implements IMutableTwinToken {
    protected MutableTwinToken twin;

    public void setTwin(MutableTwinToken twin) {
        this.twin = twin;
    }
    public MutableTwinToken getTwin() {
        return twin;
    }
}
