package com.deezmods.unifiedui.extension.admin.essentials.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.UUID;

public class SystemCommandSender implements CommandSender {

	public static SystemCommandSender INSTANCE = new SystemCommandSender();

	@Override
	public String getDisplayName() {
		return "[UnifiedUI: Admin Tooling]";
	}

	@Override
	public UUID getUuid() {
		return UUID.fromString("89a5f8fa-15ba-4e3b-a2e2-5d7a1e541421");
	}

	@Override
	public boolean hasPermission(@NonNullDecl String s) {
		return true;
	}

	@Override
	public boolean hasPermission(@NonNullDecl String s, boolean b) {
		return true;
	}

	@Override
	public void sendMessage(@NonNullDecl Message message) {}
}
