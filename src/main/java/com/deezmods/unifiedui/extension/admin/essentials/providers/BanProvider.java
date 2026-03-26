package com.deezmods.unifiedui.extension.admin.essentials.providers;

import com.deezmods.unifiedui.extension.admin.essentials.Plugin;
import com.deezmods.unifiedui.extension.admin.essentials.enums.TranslationCode;
import com.deezmods.unifiedui.extension.admin.essentials.models.BanFileItem;
import com.deezmods.unifiedui.extension.admin.essentials.services.LoggerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
import com.hypixel.hytale.server.core.modules.accesscontrol.ban.InfiniteBan;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleBanProvider;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BanProvider {
	private static final Gson GSON = new GsonBuilder().create();
	private static @Nullable LoggerService logger;
	private static @Nullable HytaleBanProvider banProvider;
	private static @Nullable File bannedFile;

	private static LoggerService getLogger() {
		if (logger == null) {
			logger = Plugin.logger;
		}
		return logger;
	}

	private static HytaleBanProvider getBanProvider() {
		if (banProvider == null) {
			try {
				var banProviderField = AccessControlModule.class.getDeclaredField("banProvider");
				banProviderField.setAccessible(true);
				banProvider = (HytaleBanProvider) banProviderField.get(AccessControlModule.get());
			} catch (Exception e) {
				getLogger().LogException(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
		return banProvider;
	}

	private static File getBannedFile() {
		if (bannedFile == null) {
			bannedFile = new File("bans.json");
		}
		return bannedFile;
	}

	public static Boolean isPlayerBaned(UUID playerId) {
		return getBanProvider().hasBan(playerId);
	}

	public static void addBan(UUID targetPlayerId, UUID fromPlayerId) {
		var ban = new InfiniteBan(
			targetPlayerId,
			fromPlayerId,
			Instant.now(),
			TranslationCode.UI_FEATURE_PLAYER_LIST_ACTION_BAN_REASON.toTranslationString());
		getBanProvider().modify(uuids -> {
			uuids.put(targetPlayerId, ban);
			return true;
		});
	}

	public static void removeBan(UUID playerId) {
		getBanProvider().modify(uuids -> {
			uuids.remove(playerId);
			return true;
		});
	}

	public static List<UUID> getBannedPlayerIds() {
		try {
			try (FileReader reader = new FileReader(getBannedFile())) {
				Type listType = new TypeToken<List<BanFileItem>>() {
				}.getType();
				List<BanFileItem> banned = GSON.fromJson(reader, listType);
				return banned.stream().map(BanFileItem::target).toList();
			}
		} catch (Exception e) {
			getLogger().LogException(TranslationCode.EXCEPTION_FILE_READ_BANS.toTranslationString(), e);
			throw new RuntimeException(e);
		}
	}
}
