package io.skyfallsdk.world.block.data;

public abstract class NumericalData implements BlockData<Byte> {

    protected volatile byte state;

    public NumericalData(byte defaultState) {
        this.state = defaultState;
    }

    @Override
    public Byte getState() {
        return state;
    }

    @Override
    public void setState(Byte state) {
        this.state = state;
    }
}
