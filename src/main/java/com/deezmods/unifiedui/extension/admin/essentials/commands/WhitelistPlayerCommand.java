package com.deezmods.unifiedui.extension.admin.essentials.commands;

import com.deezmods.unifiedui.api.definitions.CommandArgumentDefinition;
import com.deezmods.unifiedui.api.definitions.CommandDefinition;
import com.deezmods.unifiedui.api.enums.CommandSortingGroup;
import com.deezmods.unifiedui.extension.admin.essentials.Plugin;
import com.deezmods.unifiedui.extension.admin.essentials.constants.Permission;
import com.deezmods.unifiedui.extension.admin.essentials.enums.ExtensionId;
import com.deezmods.unifiedui.extension.admin.essentials.enums.TranslationCode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WhitelistPlayerCommand {
	public static CommandDefinition create(PlayerRef playerRef) {
		var builder = new CommandDefinition.Builder(
			ExtensionId.COMMAND_WHITELIST_PLAYER.id,
			TranslationCode.COMMAND_WHITELIST_PLAYER_DISPLAY_NAME.code,
			TranslationCode.COMMAND_WHITELIST_PLAYER_DESCRIPTION.code
		);

		builder.withSortingGroup(CommandSortingGroup.ADMIN);
		builder.withPermissionFilters(List.of(Permission.ADMIN));

		builder.withCommandArgs(List.of(
			new CommandArgumentDefinition.Builder(
				"playerName",
				TranslationCode.COMMAND_WHITELIST_PLAYER_ARG_DISPLAY_NAME.code
			)
				.withIsRequired(true)
				.build()
		));

		builder.withEventCallback((data) -> CompletableFuture.supplyAsync(() -> {
			var playerName = data
				.getOrDefault("playerName", "")
				.replaceAll(" ", "");

			if (playerName.isEmpty()) {
				var message = Message.join(
					TranslationCode.MESSAGE_WHITELIST_PLAYER_INVALID.toTranslationMessage(),
					Message.raw("\n- %s".formatted(playerName))
				);
				playerRef.sendMessage(message);
				return null;
			}
			CommandManager.get().handleCommand(SystemCommandSender.create(playerRef), "whitelist add %s".formatted(playerName));
			Plugin.logger.LogInfo("Whitelisted - target:%s - by:%s".formatted(playerName, playerRef.getUsername()));
			return null;
		}));

		return builder.build();
	}
}
