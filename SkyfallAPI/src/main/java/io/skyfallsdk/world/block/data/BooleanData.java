package io.skyfallsdk.world.block.data;

public abstract class BooleanData implements BlockData<Boolean> {

    protected volatile boolean state;

    public BooleanData(boolean defaultState) {
        this.state = defaultState;
    }

    @Override
    public Boolean getState() {
        return this.state;
    }

    @Override
    public void setState(Boolean state) {
        this.state = state;
    }
}
