package com.deezmods.unifiedui.extension.admin.essentials.providers;

import com.deezmods.unifiedui.extension.admin.essentials.Plugin;
import com.deezmods.unifiedui.extension.admin.essentials.services.LoggerService;
import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class WhitelistProvider {
	private static @Nullable LoggerService logger;
	private static @Nullable HytaleWhitelistProvider whitelistProvider;

	private static LoggerService getLogger() {
		if (logger == null) {
			logger = Plugin.logger;
		}
		return logger;
	}

	private static HytaleWhitelistProvider getWhitelistProvider() {
		if (whitelistProvider == null) {
			try {
				var banProviderField = AccessControlModule.class.getDeclaredField("whitelistProvider");
				banProviderField.setAccessible(true);
				whitelistProvider = (HytaleWhitelistProvider) banProviderField.get(AccessControlModule.get());
			} catch (Exception e) {
				getLogger().LogException(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
		return whitelistProvider;
	}

	public static boolean isWhitelisted(UUID playerId) {
		return getWhitelistProvider().getList().stream().anyMatch(x -> x.equals(playerId));
	}

	public static void addWhitelist(UUID playerId) {
		getWhitelistProvider().modify(uuids -> {
			uuids.add(playerId);
			return true;
		});
	}

	public static void removeWhitelist(UUID playerId) {
		getWhitelistProvider().modify(uuids -> {
			uuids.remove(playerId);
			return true;
		});
	}

	public static boolean isWhitelistEnabled() {
		return getWhitelistProvider().isEnabled();
	}

	public static List<UUID> getAllPlayerIds() {
		return getWhitelistProvider().getList().stream().toList();
	}
}
