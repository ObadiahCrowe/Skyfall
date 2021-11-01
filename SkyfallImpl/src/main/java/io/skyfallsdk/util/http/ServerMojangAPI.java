package io.skyfallsdk.util.http;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.player.PlayerProperties;
import io.skyfallsdk.util.http.response.JsonBodyHandler;
import io.skyfallsdk.util.http.response.ResponseNameHistory;
import io.skyfallsdk.util.http.response.ResponseNameToUuid;
import io.skyfallsdk.util.http.response.ResponseUuidAtTime;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ServerMojangAPI implements MojangAPI {

    private static final String GET_UUID_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String GET_NAME_HISTORY_URL = "https://api.mojang.com/user/profiles/";
    private static final Map<String, UUID> USERNAME_TO_UUID = Maps.newConcurrentMap();
    private static final HttpClient CLIENT = HttpClient.newBuilder()
      .executor(ThreadPool.createForSpec(PoolSpec.PLAYERS))
      .connectTimeout(Duration.ofSeconds(5L))
      .build();

    @Override
    public CompletableFuture<UUID> getUuidAtTime(String username, long timestamp) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_UUID_URL + username + "?at=" + timestamp))
          .header("accept", "application/json")
          .GET()
          .build();

        return CLIENT.sendAsync(request, new JsonBodyHandler<>(ResponseUuidAtTime.class)).thenApply(output -> output.body().get().getUuid());
    }

    @Override
    public CompletableFuture<List<ResponseNameHistory>> getNameHistory(UUID uuid) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_NAME_HISTORY_URL + uuid.toString() + "/names"))
          .header("accept", "application/json")
          .GET()
          .build();

        TypeToken<List<ResponseNameHistory>> type = new TypeToken<>() {};

        return CLIENT.sendAsync(request, new JsonBodyHandler<List<ResponseNameHistory>>((Class<List<ResponseNameHistory>>) type.getRawType())).thenApply(output -> output.body().get());
    }

    @Override
    public CompletableFuture<List<ResponseNameToUuid>> getUuidsFromUsernames(Collection<String> usernames) {
        return null;
    }

    @Override
    public CompletableFuture<PlayerProperties> getPlayerProperties(UUID uuid) {
        return null;
    }
}
