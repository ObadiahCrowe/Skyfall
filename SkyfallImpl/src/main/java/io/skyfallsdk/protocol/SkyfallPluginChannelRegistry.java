package io.skyfallsdk.protocol;

import com.google.common.collect.Maps;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.protocol.channel.PluginChannel;
import io.skyfallsdk.protocol.channel.PluginChannelRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class SkyfallPluginChannelRegistry implements PluginChannelRegistry {

    private final Map<String, SkyfallPluginChannel> registeredChannels;

    public SkyfallPluginChannelRegistry() {
        this.registeredChannels = Maps.newConcurrentMap();
    }

    @Override
    public @NotNull PluginChannel registerPluginChannel(@NotNull Expansion expansion, @NotNull String channelId) {
        return null;
    }

    @Override
    public <T extends PluginChannel> @NotNull T registerPluginChannel(@NotNull Expansion expansion, @NotNull String channelId, @NotNull T channel) {
        return null;
    }

    @Override
    public void deregisterPluginChannel(@NotNull String channelId) {

    }

    @Override
    public void deregisterPluginChannel(@NotNull PluginChannel channel) {

    }

    @Override
    public @NotNull Optional<@Nullable PluginChannel> getChannel(@NotNull String channelId) {
        return Optional.empty();
    }

    @Override
    public @NotNull Collection<@NotNull PluginChannel> getRegisteredChannels() {
        return Collections.unmodifiableCollection(this.registeredChannels.values());
    }
}
