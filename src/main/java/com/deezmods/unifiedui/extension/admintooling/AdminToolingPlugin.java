package com.deezmods.unifiedui.extension.admintooling;

import com.deezmods.unifiedui.api.UuiApi;
import com.deezmods.unifiedui.api.service.LoggerService;
import com.deezmods.unifiedui.extension.admintooling.commands.PluginCommands;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class AdminToolingPlugin extends JavaPlugin {

    private final LoggerService logger = new LoggerService();

    public AdminToolingPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    public CompletableFuture<Void> preLoad() {
        super.preLoad();
        UuiApi.registerExtension(new AdminToolingExtension());
        return CompletableFuture.completedFuture(null);
    }


    @Override
    protected void start() {
        var registry = this.getCommandRegistry();
        registry.registerCommand(new PluginCommands());
    }

}