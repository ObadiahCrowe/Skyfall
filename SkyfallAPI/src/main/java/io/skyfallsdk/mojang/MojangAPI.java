package io.skyfallsdk.mojang;

import io.skyfallsdk.mojang.response.ResponseNameHistory;
import io.skyfallsdk.mojang.response.ResponseNameToUuid;
import io.skyfallsdk.player.PlayerProperties;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

@ThreadSafe
public interface MojangAPI {

    Future<UUID> getUuidAtTime(String username, long timestamp);

    Future<List<ResponseNameHistory>> getNameHistory(UUID uuid);

    default Future<List<ResponseNameToUuid>> getUuidsFromUsernames(String... usernames) {
        return this.getUuidsFromUsernames(Arrays.asList(usernames));
    }

    Future<List<ResponseNameToUuid>> getUuidsFromUsernames(Collection<String> usernames);

    Future<PlayerProperties> getPlayerProperties(UUID uuid);
}
