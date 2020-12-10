package io.skyfallsdk.util.http;

import io.skyfallsdk.player.PlayerProperties;
import io.skyfallsdk.util.http.response.ResponseNameHistory;
import io.skyfallsdk.util.http.response.ResponseNameToUuid;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ThreadSafe
public interface MojangAPI {

    /**
     * Obtains a UUID from a player's username.
     *
     * @param username Username of the player to obtain the UUID of.
     *
     * @return The corresponding UUID, {@return null} if the player does not exist.
     */
    default CompletableFuture<UUID> getUuid(String username) {
        return this.getUuidAtTime(username, System.currentTimeMillis());
    }

    CompletableFuture<UUID> getUuidAtTime(String username, long timestamp);

    CompletableFuture<List<ResponseNameHistory>> getNameHistory(UUID uuid);

    default CompletableFuture<List<ResponseNameToUuid>> getUuidsFromUsernames(String... usernames) {
        return this.getUuidsFromUsernames(Arrays.asList(usernames));
    }

    CompletableFuture<List<ResponseNameToUuid>> getUuidsFromUsernames(Collection<String> usernames);

    CompletableFuture<PlayerProperties> getPlayerProperties(UUID uuid);
}
