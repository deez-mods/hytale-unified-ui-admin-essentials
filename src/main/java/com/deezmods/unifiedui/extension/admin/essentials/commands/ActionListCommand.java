package com.deezmods.unifiedui.extension.admin.essentials.commands;

import com.deezmods.unifiedui.api.definitions.CommandArgumentDefinition;
import com.deezmods.unifiedui.api.definitions.CommandDefinition;
import com.deezmods.unifiedui.api.enums.CommandArgInputType;
import com.deezmods.unifiedui.api.enums.CommandSortingGroup;
import com.deezmods.unifiedui.extension.admin.essentials.enums.ExtensionId;
import com.deezmods.unifiedui.extension.admin.essentials.enums.Permissions;
import com.deezmods.unifiedui.extension.admin.essentials.enums.TranslationCode;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ActionListCommand {
	public static CommandDefinition create(PlayerRef playerRef) {
		var builder = new CommandDefinition.Builder(
			ExtensionId.COMMAND_ACTIONS.id,
			TranslationCode.COMMAND_ACTIONS_DISPLAY_NAME.code,
			TranslationCode.COMMAND_ACTIONS_DESCRIPTION.code
		);

		builder.withSortingGroup(CommandSortingGroup.ADMIN);
		builder.withPermissionFilters(List.of(Permissions.COMMANDS.id));

		builder.withCommandArgs(List.of(
			new CommandArgumentDefinition.Builder(
				"action",
				TranslationCode.COMMAND_ACTIONS_ARG_DISPLAY_NAME.code
			)
				.withIsRequired(true)
				.withCommandArgInputType(CommandArgInputType.Dropdown)
				.withDefaultOptions(Map.of(
					"whitelist-enable", TranslationCode.COMMAND_ACTIONS_ARG_DROPDOWN_KEY_WHITELIST_ENABLE.code,
					"whitelist-disable", TranslationCode.COMMAND_ACTIONS_ARG_DROPDOWN_KEY_WHITELIST_DISABLE.code,
					"whitelist-add-self", TranslationCode.COMMAND_ACTIONS_ARG_DROPDOWN_KEY_WHITELIST_ADD_SELF.code
				))
				.build()
		));

		builder.withEventCallback((data) -> {
			var action = data.getOrDefault("action", null);
			switch (action) {
				case "whitelist-enable" -> {
					CommandManager
						.get()
						.handleCommand(SystemCommandSender.INSTANCE, "whitelist enable");
					playerRef.sendMessage(TranslationCode.MESSAGE_WHITELIST_ENABLED.toTranslationMessage());
				}
				case "whitelist-disable" -> {
					CommandManager
						.get()
						.handleCommand(SystemCommandSender.INSTANCE, "whitelist disable");
					playerRef.sendMessage(TranslationCode.MESSAGE_WHITELIST_DISABLED.toTranslationMessage());
				}
				case "whitelist-add-self" -> {
					CommandManager
						.get()
						.handleCommand(SystemCommandSender.INSTANCE, "whitelist add %s".formatted(playerRef.getUsername()));
					playerRef.sendMessage(TranslationCode.MESSAGE_WHITELIST_ADDED_SELF.toTranslationMessage());
				}
				default -> {
				}
			}
			return CompletableFuture.completedFuture(null);
		});

		return builder.build();
	}
}
