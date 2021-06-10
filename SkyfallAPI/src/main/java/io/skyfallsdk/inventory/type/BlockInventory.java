package io.skyfallsdk.inventory.type;

import io.skyfallsdk.inventory.Inventory;
import io.skyfallsdk.world.Position;

public interface BlockInventory extends Inventory {

    Position getPosition();
}
