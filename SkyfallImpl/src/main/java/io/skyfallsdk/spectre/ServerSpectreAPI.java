package io.skyfallsdk.spectre;

import com.google.common.collect.Lists;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.config.ServerConfig;
import io.skyfallsdk.expansion.ExpansionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerSpectreAPI implements SpectreAPI {

    private final SkyfallServer server;
    private final ServerConfig.ServerSpectreConfig config;

    public ServerSpectreAPI(SkyfallServer server) {
        this.server = server;
        this.config = this.server.getConfig().getSpectreConfig();
    }

    @Override
    public @NotNull List<@NotNull Long> getMatchingExpansions(@NotNull String searchQuery) {
        if (!this.config.isEnabled()) {
            return Lists.newArrayList();
        }

        return null;
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Void> downloadExpansion(long expansionId) {
        if (!this.config.isEnabled()) {
            return CompletableFuture.failedFuture(new Throwable("Spectre is not presently enabled. If you wish to use it, enable it in 'config.yml'."));
        }

        return null;
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Void> installExpansion(long expansionId) {
        if (!this.config.isEnabled()) {
            return CompletableFuture.failedFuture(new Throwable("Spectre is not presently enabled. If you wish to use it, enable it in 'config.yml'."));
        }

        return null;
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Boolean> hasUpdate(long expansionId) {
        if (!this.config.isEnabled()) {
            return CompletableFuture.failedFuture(new Throwable("Spectre is not presently enabled. If you wish to use it, enable it in 'config.yml'."));
        }

        return null;
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Void> uninstallExpansion(long expansionId) {
        if (!this.config.isEnabled()) {
            return CompletableFuture.failedFuture(new Throwable("Spectre is not presently enabled. If you wish to use it, enable it in 'config.yml'."));
        }

        return null;
    }

    @Override
    public @NotNull CompletableFuture<@Nullable ExpansionInfo> getExpansionInfo(long expansionId) {
        if (!this.config.isEnabled()) {
            return CompletableFuture.failedFuture(new Throwable("Spectre is not presently enabled. If you wish to use it, enable it in 'config.yml'."));
        }

        return null;
    }
}
