package io.skyfallsdk.world.block.data;

import io.skyfallsdk.world.block.state.BlockDirection;

public abstract class DirectionalData implements BlockData<BlockDirection> {

    protected BlockDirection state;

    public DirectionalData(BlockDirection defaultState) {
        this.state = defaultState;
    }

    @Override
    public BlockDirection getState() {
        return this.state;
    }

    @Override
    public void setState(BlockDirection state) {
        this.state = state;
    }
}
