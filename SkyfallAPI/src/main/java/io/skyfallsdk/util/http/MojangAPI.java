package io.skyfallsdk.util.http;

import io.skyfallsdk.concurrent.ThreadSafe;
import io.skyfallsdk.player.PlayerProperties;
import io.skyfallsdk.util.http.response.ResponseNameHistory;
import io.skyfallsdk.util.http.response.ResponseNameToUuid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    default @NotNull CompletableFuture<@Nullable UUID> getUuid(@NotNull String username) {
        return this.getUuidAtTime(username, System.currentTimeMillis());
    }

    @NotNull CompletableFuture<@Nullable UUID> getUuidAtTime(@NotNull String username, long timestamp);

    @NotNull CompletableFuture<@NotNull List<@NotNull ResponseNameHistory>> getNameHistory(@NotNull UUID uuid);

    default @NotNull CompletableFuture<@NotNull List<@NotNull ResponseNameToUuid>> getUuidsFromUsernames(@NotNull String @NotNull... usernames) {
        return this.getUuidsFromUsernames(Arrays.asList(usernames));
    }

    @NotNull CompletableFuture<@NotNull List<@NotNull ResponseNameToUuid>> getUuidsFromUsernames(@NotNull Collection<@NotNull String> usernames);

    default @NotNull CompletableFuture<@Nullable PlayerProperties> getPlayerProperties(@NotNull String username) {
        return this.getUuid(username).thenComposeAsync(uuid -> {
            if (uuid == null) {
                return null;
            }

            return this.getPlayerProperties(uuid);
        });
    }

    @NotNull CompletableFuture<@Nullable PlayerProperties> getPlayerProperties(@NotNull UUID uuid);

    @NotNull CompletableFuture<@NotNull Boolean> checkIfBlacklisted(@NotNull String domainName);
}
