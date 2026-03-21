package com.deezmods.unifiedui.extension.admin.essentials.enums;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.util.MessageUtil;

public enum TranslationCode {
	// Commands ========================================================================================================
	COMMAND_ACTIONS_DISPLAY_NAME("command.actions.display.name"),
	COMMAND_ACTIONS_DESCRIPTION("command.actions.description"),
	COMMAND_ACTIONS_ARG_DISPLAY_NAME("command.actions.arg.display.name"),
	COMMAND_ACTIONS_ARG_DROPDOWN_KEY_WHITELIST_ENABLE("command.actions.arg.dropdown.key.whitelist.enable"),
	COMMAND_ACTIONS_ARG_DROPDOWN_KEY_WHITELIST_DISABLE("command.actions.arg.dropdown.key.whitelist.disable"),
	COMMAND_ACTIONS_ARG_DROPDOWN_KEY_WHITELIST_ADD_SELF("command.actions.arg.dropdown.key.whitelist.add.self"),
	MESSAGE_WHITELIST_ENABLED("message.whitelist.enabled"),
	MESSAGE_WHITELIST_DISABLED("message.whitelist.disabled"),
	MESSAGE_WHITELIST_ADDED_SELF("message.whitelist.added.self"),
	UI_FEATURE_PLAYER_LIST_MENU_NAME("ui.feature.player.list.menu.name"),
	UI_FEATURE_PLAYER_LIST_TITLE("ui.feature.player.list.title"),
	UI_FEATURE_PLAYER_LIST_ACTION_BAN_REASON("ui.feature.player.list.action.ban.reason"),
	DEFAULT("");

	public final String code;

	TranslationCode(String code) {
		this.code = "%s.%s".formatted("com.deezmods.unifiedui.extensions.admin.essentials", code);
	}

	public Message toTranslationMessage() {
		return Message.translation(this.code);
	}

	public String toTranslationString() {
		return MessageUtil
			.toAnsiString(toTranslationMessage())
			.toString();
	}
}
