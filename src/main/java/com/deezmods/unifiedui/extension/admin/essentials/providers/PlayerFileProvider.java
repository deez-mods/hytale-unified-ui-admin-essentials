package com.deezmods.unifiedui.extension.admin.essentials.providers;

import com.deezmods.unifiedui.extension.admin.essentials.Plugin;
import com.deezmods.unifiedui.extension.admin.essentials.enums.TranslationCode;
import com.deezmods.unifiedui.extension.admin.essentials.models.PlayerFileItem;
import com.deezmods.unifiedui.extension.admin.essentials.services.LoggerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.util.UUID;

public class PlayerFileProvider {
	private static final Gson GSON = new GsonBuilder().create();
	private static @Nullable LoggerService logger;
	private static @Nullable File playerFolder;

	private static LoggerService getLogger() {
		if (logger == null) {
			logger = Plugin.logger;
		}
		return logger;
	}

	private static File getPlayerFolder() {
		if (playerFolder == null) {
			playerFolder = new File("universe", "players");
		}
		return playerFolder;
	}

	public static @Nullable PlayerFileItem getPlayer(UUID playerId) {
		try {
			var playerFile = new File(getPlayerFolder(), "%s.json".formatted(playerId));
			if (!playerFile.exists()) return null;
			try (FileReader reader = new FileReader(playerFile)) {
				return GSON.fromJson(reader, PlayerFileItem.class);
			}
		} catch (Exception e) {
			getLogger().LogException(TranslationCode.EXCEPTION_FILE_READ_PLAYER.toTranslationString(), e);
			throw new RuntimeException(e);
		}
	}
}
