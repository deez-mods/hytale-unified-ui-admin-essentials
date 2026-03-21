package com.deezmods.unifiedui.extension.admin.essentials;

import com.deezmods.unifiedui.api.UuiApi;
import com.deezmods.unifiedui.api.service.LoggerService;
import com.deezmods.unifiedui.extension.admin.essentials.providers.BanProvider;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleBanProvider;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class Plugin extends JavaPlugin {

	public static HytaleBanProvider banProvider;

	private final LoggerService logger = new LoggerService();

	public Plugin(@Nonnull JavaPluginInit init) {
		super(init);
	}


	@Override
	protected void start() {
		banProvider = BanProvider.getProvider(logger);
	}

	@Override
	public CompletableFuture<Void> preLoad() {
		super.preLoad();
		UuiApi.registerExtension(new UuiExtension());
		return CompletableFuture.completedFuture(null);
	}
}