package com.deezmods.unifiedui.extension.admintooling.services;

import com.hypixel.hytale.logger.HytaleLogger;

import javax.annotation.Nullable;
import java.util.logging.Level;

public class LoggerService {
	private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public void Log(Level logLevel, @Nullable String message) {
		var finalMessage = "[UnifiedUI: Admin Tooling] %s".formatted(
				message == null ? "No message provided" : message);
		LOGGER.at(logLevel).log(finalMessage);
	}

	public void Log(@Nullable String message, @Nullable Exception exception) {
		var finalMessage = "[UnifiedUI: Admin Tooling] %s".formatted(
			message == null ? "No message provided" : message);
		LOGGER.at(Level.SEVERE).withCause(exception).log(finalMessage);
	}

	public void LogInfo(@Nullable String message) {
		this.Log(Level.INFO, message);
	}

	public void LogWarning(@Nullable String message) {
		this.Log(Level.WARNING, message);
	}

	public void LogError(@Nullable String message) {
		this.Log(Level.SEVERE, message);
	}

	public void LogException(@Nullable String message, @Nullable Exception exception) {
		this.Log(message, exception);
	}
}
