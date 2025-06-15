package model;


public class TargetMirrorToken extends MutableToken implements ITargetToken {
    boolean requiredTarget = false;

    public TargetMirrorToken() {
        super();
    }

    public void setRequiredTarget(boolean requiredTarget) {
        this.requiredTarget = requiredTarget;
    }

    public boolean isRequiredTarget() {
        return requiredTarget;
    }

}