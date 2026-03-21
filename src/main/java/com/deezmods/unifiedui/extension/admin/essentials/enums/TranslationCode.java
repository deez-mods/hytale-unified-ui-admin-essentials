package com.deezmods.unifiedui.extension.admin.essentials.enums;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.util.MessageUtil;

public enum TranslationCode {
	// Commands ========================================================================================================
	COMMAND_ARG_DROPDOWN_KEY_WHITELIST_ENABLE("command.arg.dropdown.key.whitelist.enable"),
	COMMAND_ARG_DROPDOWN_KEY_WHITELIST_DISABLE("command.arg.dropdown.key.whitelist.disable"),
	COMMAND_ARG_DROPDOWN_KEY_WHITELIST_ADD_SELF("command.arg.dropdown.key.whitelist.add.self"),
	MESSAGE_WHITELIST_ENABLED("message.whitelist.enabled"),
	MESSAGE_WHITELIST_DISABLED("message.whitelist.disabled"),
	MESSAGE_WHITELIST_ADDED_SELF("message.whitelist.added.self"),
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
