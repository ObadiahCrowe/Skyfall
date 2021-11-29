package io.skyfallsdk.util.http;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.player.PlayerProperties;
import io.skyfallsdk.util.http.response.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ServerMojangAPI implements MojangAPI {

    private static final String GET_UUID_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String GET_NAME_HISTORY_URL = "https://api.mojang.com/user/profiles/";
    private static final String NAME_TO_UUID_URL = "https://api.mojang.com/profiles/minecraft";
    private static final String GET_PROFILE_AND_SKIN_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final String GET_BLOCKED_SERVERS_URL = "https://sessionserver.mojang.com/blockedservers";
    private static final Map<String, UUID> USERNAME_TO_UUID = Maps.newConcurrentMap();
    private static final HttpClient CLIENT = HttpClient.newBuilder()
      .executor(ThreadPool.createForSpec(PoolSpec.PLAYERS))
      .connectTimeout(Duration.ofSeconds(5L))
      .build();

    @Override
    public @NotNull CompletableFuture<@Nullable UUID> getUuidAtTime(@NotNull String username, long timestamp) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_UUID_URL + username + "?at=" + timestamp))
          .header("accept", "application/json")
          .GET()
          .build();

        return CLIENT.sendAsync(request, new JsonBodyHandler<>(ResponseUuidAtTime.class)).thenApply(output -> output.body().get().getUuid());
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull CompletableFuture<@NotNull List<@NotNull ResponseNameHistory>> getNameHistory(@NotNull UUID uuid) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_NAME_HISTORY_URL + uuid + "/names"))
          .header("accept", "application/json")
          .GET()
          .build();

        return CLIENT.sendAsync(request, new JsonBodyHandler<>((Class<List<ResponseNameHistory>>) JsonBodyHandler.ResponseNameHistoryAdapter.TYPE_TOKEN.getRawType())).thenApply(output -> output.body().get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull CompletableFuture<@NotNull List<@NotNull ResponseNameToUuid>> getUuidsFromUsernames(@NotNull Collection<@NotNull String> usernames) {
        if (usernames.size() > 10) {
            throw new IllegalArgumentException("You cannot request more than 10 uuids per request.");
        }

        HttpRequest request = HttpRequest.newBuilder(URI.create(NAME_TO_UUID_URL))
          .header("accept", "application/json")
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(JsonBodyHandler.RESPONSE_GSON.toJson(usernames)))
          .build();

        return CLIENT.sendAsync(request, new JsonBodyHandler<>((Class<List<ResponseNameToUuid>>) JsonBodyHandler.ResponseNameToUuidAdapter.TYPE_TOKEN.getRawType())).thenApply(output -> output.body().get());
    }

    @Override
    public @NotNull CompletableFuture<@Nullable PlayerProperties> getPlayerProperties(@NotNull UUID uuid) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_PROFILE_AND_SKIN_URL + uuid))
          .header("accept", "application/json")
          .GET()
          .build();

        return CLIENT.sendAsync(request, new JsonBodyHandler<>(ResponseUuidToProperties.class)).thenApply(output -> {
            ResponseUuidToProperties properties = output.body().get();

            if (properties == null) {
                return null;
            }

            return properties.getProperties();
        });
    }

    @Override
    @SuppressWarnings(value = { "UnstableApiUsage", "deprecation" })
    public @NotNull CompletableFuture<@NotNull Boolean> checkIfBlacklisted(@NotNull String domainName) {
        String hashedDomain = Hashing.sha1().hashString(domainName.toLowerCase(Locale.ROOT), Charsets.ISO_8859_1).toString();
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_BLOCKED_SERVERS_URL))
          .GET()
          .build();

        return CLIENT.sendAsync(request, new PlainTextBodyHandler()).thenApply(output -> output.body().contains(hashedDomain));
    }
}
