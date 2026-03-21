package com.deezmods.unifiedui.extension.admin.essentials.providers;

import com.deezmods.unifiedui.api.service.LoggerService;
import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleBanProvider;

import javax.annotation.Nullable;

public class BanProvider {
	public static @Nullable HytaleBanProvider getProvider(LoggerService logger) {
		try {
			var banProviderField = AccessControlModule.class.getDeclaredField("banProvider");
			banProviderField.setAccessible(true);
			return (HytaleBanProvider) banProviderField.get(AccessControlModule.get());
		} catch (Exception e) {
			logger.LogException(e.getMessage(), e);
		}
		return null;
	}
}
