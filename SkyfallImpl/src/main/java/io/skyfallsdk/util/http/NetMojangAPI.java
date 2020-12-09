package io.skyfallsdk.util.http;

import com.google.common.collect.Maps;
import io.skyfallsdk.SkyfallServer;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class NetMojangAPI implements MojangAPI {

    private static final String GET_UUID_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final Map<String, UUID> USERNAME_TO_UUID = Maps.newConcurrentMap();

    private final SkyfallServer server;

    public NetMojangAPI(SkyfallServer server) {
        this.server = server;
    }

    @Override
    public CompletableFuture<UUID> getUuidAtTime(String username, long timestamp) {
        HttpClient client = HttpClient.newBuilder()
          .executor(ThreadPool.createForSpec(PoolSpec.PLAYERS))
          .build();

        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_UUID_URL + username + "?at=" + timestamp))
          .header("accept", "application/json")
          .GET()
          .build();

        return client.sendAsync(request, new JsonBodyHandler<>(ResponseUuidAtTime.class)).thenApply(output -> output.body().get().getUuid());
    }

    @Override
    public Future<List<ResponseNameHistory>> getNameHistory(UUID uuid) {
        return null;
    }

    @Override
    public Future<List<ResponseNameToUuid>> getUuidsFromUsernames(Collection<String> usernames) {
        return null;
    }

    @Override
    public Future<PlayerProperties> getPlayerProperties(UUID uuid) {
        return null;
    }
}
