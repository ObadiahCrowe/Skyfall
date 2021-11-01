package io.skyfallsdk.protocol.channel;

import io.skyfallsdk.expansion.Expansion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface PluginChannelRegistry {

    @NotNull
    PluginChannel registerPluginChannel(@NotNull Expansion expansion, @NotNull String channelId);

    @NotNull
    <T extends PluginChannel> T registerPluginChannel(@NotNull Expansion expansion, @NotNull String channelId, @NotNull T channel);

    void deregisterPluginChannel(@NotNull String channelId);

    void deregisterPluginChannel(@NotNull PluginChannel channel);

    @NotNull Optional<@Nullable PluginChannel> getChannel(@NotNull String channelId);

    @NotNull Collection<@NotNull PluginChannel> getRegisteredChannels();
}
