package io.skyfallsdk;

import io.skyfallsdk.bossbar.BossBar;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.tick.TickRegistry;
import io.skyfallsdk.concurrent.tick.TickSpec;
import io.skyfallsdk.enchantment.EnchantmentRegistry;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.expansion.ExpansionRegistry;
import io.skyfallsdk.inventory.InventoryFactory;
import io.skyfallsdk.item.ItemFactory;
import io.skyfallsdk.protocol.channel.PluginChannel;
import io.skyfallsdk.protocol.channel.PluginChannelRegistry;
import io.skyfallsdk.server.ServerState;
import io.skyfallsdk.spectre.SpectreAPI;
import io.skyfallsdk.util.http.MojangAPI;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.ServerCommandSender;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.WorldLoader;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public interface Server extends ServerCommandSender {

    @NotNull
    static Server get() {
        return Impl.IMPL.get();
    }

    void shutdown();

    @NotNull
    ServerState getState();

    @NotNull
    Path getPath();

    @NotNull
    Logger getLogger();

    @NotNull
    Logger getLogger(Class<? extends Expansion> expansion);

    @NotNull
    default Logger getLogger(Expansion expansion) {
        return this.getLogger(expansion.getClass());
    }

    @NotNull
    Scheduler getScheduler();

    @NotNull
    CommandMap getCommandMap();

    @NotNull Optional<@Nullable Player> getPlayer(String username);

    @NotNull Optional<@Nullable Player> getPlayer(UUID uuid);

    @NotNull
    List<@NotNull Player> getPlayers();

    @NotNull
    Collection<@NotNull World> getWorlds();

    @NotNull
    CompletableFuture<@NotNull Optional<@Nullable World>> getWorld(@NotNull String name);

    @NotNull
    ChatComponent getMotd();

    void setMotd(@NotNull ChatComponent motd);

    @Nullable
    Path getServerIcon();

    boolean isOnlineMode();

    void setOnlineMode(boolean onlineMode);

    int getMaxPlayers();

    void setMaxPlayers(int maxPlayers);

    @NotNull
    ExpansionRegistry getExpansionRegistry();

    @NotNull
    PluginChannelRegistry getPluginChannelRegistry();

    @Nullable
    <T extends Expansion> T getExpansion(@NotNull Class<T> expansionClass);

    @Nullable
    default ExpansionInfo getExpansionInfo(@NotNull Expansion expansion) {
        return this.getExpansionInfo(expansion.getClass());
    }

    @Nullable
    ExpansionInfo getExpansionInfo(@NotNull Class<? extends Expansion> expansionClass);

    @NotNull
    SpectreAPI getSpectreAPI();

    @NotNull
    MojangAPI getMojangApi();

    @NotNull
    List<@NotNull ProtocolVersion> getSupportedVersions();

    @NotNull
    ProtocolVersion getBaseVersion();

    @NotNull
    WorldLoader getWorldLoader();

    @NotNull
    <T extends TickSpec<T>> TickRegistry<T> getTickRegistry(@NotNull T spec);

    @NotNull
    Collection<? extends @NotNull TickRegistry<?>> getTickRegisteries();

    @NotNull
    ItemFactory getItemFactory();

    @NotNull
    InventoryFactory getInventoryFactory();

    @NotNull
    EnchantmentRegistry getEnchantmentRegistry();

    @NotNull
    BossBar createNewBossBar();

    default @NotNull Optional<@Nullable PluginChannel> getChannel(@NotNull String channelId) {
        return this.getPluginChannelRegistry().getChannel(channelId);
    }

    default @NotNull PluginChannel getOrCreateChannel(@NotNull Expansion expansion, @NotNull String channelId) {
        return this.getPluginChannelRegistry().getChannel(channelId).orElse(this.getPluginChannelRegistry().registerPluginChannel(expansion, channelId));
    }

    static class Impl {
        static AtomicReference<Server> IMPL = new AtomicReference<>(null);
    }
}
