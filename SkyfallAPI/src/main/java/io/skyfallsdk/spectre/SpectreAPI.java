package io.skyfallsdk.spectre;

import io.skyfallsdk.expansion.ExpansionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SpectreAPI {

    @NotNull
    List<@NotNull Long> getMatchingExpansions(@NotNull String searchQuery);

    @NotNull
    CompletableFuture<@NotNull Void> downloadExpansion(long expansionId);

    @NotNull
    CompletableFuture<@NotNull Void> installExpansion(long expansionId);

    @NotNull
    CompletableFuture<@NotNull Boolean> hasUpdate(long expansionId);

    @NotNull
    CompletableFuture<@NotNull Void> uninstallExpansion(long expansionId);

    @NotNull
    CompletableFuture<@Nullable ExpansionInfo> getExpansionInfo(long expansionId);
}
