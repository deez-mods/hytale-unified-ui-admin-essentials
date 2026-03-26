package com.deezmods.unifiedui.extension.admin.essentials.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nullable;
import java.util.UUID;

public class SystemCommandSender implements CommandSender {

	public static SystemCommandSender INSTANCE = new SystemCommandSender();

	private final @Nullable PlayerRef playerCaller;

	private SystemCommandSender() {
		this.playerCaller = null;
	}

	private SystemCommandSender(@NullableDecl PlayerRef playerCaller) {
		this.playerCaller = playerCaller;
	}

	public static SystemCommandSender create(PlayerRef playerCaller) {
		return new SystemCommandSender(playerCaller);
	}


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
	public void sendMessage(@NonNullDecl Message message) {
		if (this.playerCaller != null && this.playerCaller.isValid()) {
			this.playerCaller.sendMessage(Message.join(
				Message.raw("[UnifiedUI: Admin Tooling]\n- "),
				message
			));
		}
	}
}
