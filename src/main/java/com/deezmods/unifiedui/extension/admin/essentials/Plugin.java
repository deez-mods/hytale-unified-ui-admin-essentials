package com.deezmods.unifiedui.extension.admin.essentials;

import com.deezmods.unifiedui.api.UuiApi;
import com.deezmods.unifiedui.extension.admin.essentials.services.LoggerService;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class Plugin extends JavaPlugin {

	public static LoggerService logger = new LoggerService();

	public Plugin(@Nonnull JavaPluginInit init) {
		super(init);
	}

	@Override
	public CompletableFuture<Void> preLoad() {
		super.preLoad();
		UuiApi.registerExtension(new UuiExtension(), this);
		return CompletableFuture.completedFuture(null);
	}
}