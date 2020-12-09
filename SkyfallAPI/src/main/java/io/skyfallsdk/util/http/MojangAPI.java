package io.skyfallsdk.util.http;

import io.skyfallsdk.util.http.response.ResponseNameHistory;
import io.skyfallsdk.util.http.response.ResponseNameToUuid;
import io.skyfallsdk.player.PlayerProperties;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@ThreadSafe
public interface MojangAPI {

    default CompletableFuture<UUID> getUuid(String username) {
        return this.getUuidAtTime(username, System.currentTimeMillis());
    }

    CompletableFuture<UUID> getUuidAtTime(String username, long timestamp);

    Future<List<ResponseNameHistory>> getNameHistory(UUID uuid);

    default Future<List<ResponseNameToUuid>> getUuidsFromUsernames(String... usernames) {
        return this.getUuidsFromUsernames(Arrays.asList(usernames));
    }

    Future<List<ResponseNameToUuid>> getUuidsFromUsernames(Collection<String> usernames);

    Future<PlayerProperties> getPlayerProperties(UUID uuid);
}
