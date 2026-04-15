package com.deezmods.unifiedui.extension.admin.essentials.features;

import com.deezmods.unifiedui.api.definitions.FeatureDefinition;
import com.deezmods.unifiedui.api.definitions.FeatureEventDefinition;
import com.deezmods.unifiedui.extension.admin.essentials.Plugin;
import com.deezmods.unifiedui.extension.admin.essentials.commands.SystemCommandSender;
import com.deezmods.unifiedui.extension.admin.essentials.constants.Permission;
import com.deezmods.unifiedui.extension.admin.essentials.enums.ExtensionId;
import com.deezmods.unifiedui.extension.admin.essentials.enums.PlayerListActions;
import com.deezmods.unifiedui.extension.admin.essentials.enums.TranslationCode;
import com.deezmods.unifiedui.extension.admin.essentials.models.PlayerListItem;
import com.deezmods.unifiedui.extension.admin.essentials.providers.BanProvider;
import com.deezmods.unifiedui.extension.admin.essentials.providers.PlayerFileProvider;
import com.deezmods.unifiedui.extension.admin.essentials.providers.WhitelistProvider;
import com.deezmods.unifiedui.extension.admin.essentials.ui.PlayerListFeatureController;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerListFeature {
	private static final PlayerListFeatureController playerListFeatureController = new PlayerListFeatureController("");

	public static FeatureDefinition create(PlayerRef playerRef) {
		var playerList = new AtomicReference<List<PlayerListItem>>(new ArrayList<>());

		var builder = new FeatureDefinition.Builder(
			ExtensionId.PLAYER_LIST.id,
			TranslationCode.UI_FEATURE_PLAYER_LIST_MENU_NAME.code
		);
		builder.withPermissionFilters(List.of(Permission.ADMIN));
		builder.withBuildUserInterface((uiBuilder) -> CompletableFuture.supplyAsync(() -> {
			playerList.set(getPlayersList());
			playerListFeatureController.build(uiBuilder, playerList.get());
			return null;
		}));

		builder.withGetEventDefinitions(() -> CompletableFuture.supplyAsync(() -> {
			var players = playerList.get();
			var events = new ArrayList<FeatureEventDefinition>();

			for (var index = 0; index < players.size(); index++) {
				var player = players.get(index);

				var banSelector = "#PlayerListContainer[%s] %s".formatted(index, player.isBanned ? "#ActionButtonBanRemove" : "#ActionButtonBanAdd");
				var banAction = player.isBanned ? PlayerListActions.BAN_REMOVE.id : PlayerListActions.BAN_ADD.id;
				events.add(new FeatureEventDefinition(
					banSelector,
					banAction,
					CustomUIEventBindingType.Activating,
					Map.of(0, player.playerId.toString()),
					Collections.emptyMap()
				));

				var whitelistSelector = "#PlayerListContainer[%s] %s".formatted(index, player.isWhitelisted ? "#ActionButtonWhitelistRemove" : "#ActionButtonWhitelistAdd");
				var whitelistAction = player.isWhitelisted ? PlayerListActions.WHITELIST_REMOVE.id : PlayerListActions.WHITELIST_ADD.id;
				events.add(new FeatureEventDefinition(
					whitelistSelector,
					whitelistAction,
					CustomUIEventBindingType.Activating,
					Map.of(0, player.playerId.toString()),
					Collections.emptyMap()
				));

				events.add(new FeatureEventDefinition(
					"#PlayerListContainer[%s] #ActionButtonTpHere".formatted(index),
					PlayerListActions.TP_HERE.id,
					CustomUIEventBindingType.Activating,
					Map.of(0, player.playerId.toString()),
					Collections.emptyMap()
				));

				events.add(new FeatureEventDefinition(
					"#PlayerListContainer[%s] #ActionButtonTpThere".formatted(index),
					PlayerListActions.TP_THERE.id,
					CustomUIEventBindingType.Activating,
					Map.of(0, player.playerId.toString()),
					Collections.emptyMap()
				));

				events.add(new FeatureEventDefinition(
					"#PlayerListContainer[%s] #ActionButtonKick".formatted(index),
					PlayerListActions.KICK.id,
					CustomUIEventBindingType.Activating,
					Map.of(0, player.playerId.toString()),
					Collections.emptyMap()
				));
			}
			return events;
		}));

		builder.withHandleEvent((actionName, actionArgs, actionData) -> CompletableFuture.supplyAsync(() -> {
			var players = playerList.get();
			PlayerListActions action = PlayerListActions.fromId(actionName);
			if (action == null) return false;
			switch (action) {
				case PlayerListActions.BAN_REMOVE -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					BanProvider.removeBan(player.playerId);
					Plugin.logger.LogInfo("Unbanned - target:%s - by:%s".formatted(player.playerName, player.playerName));
				}
				case PlayerListActions.BAN_ADD -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					BanProvider.addBan(player.playerId, playerRef.getUuid());
					Plugin.logger.LogInfo("Banned - target:%s - by:%s".formatted(player.playerName, player.playerName));
					var pRef = Universe.get().getPlayer(player.playerId);
					if (pRef == null) break;
					pRef.getPacketHandler().disconnect(TranslationCode.UI_FEATURE_PLAYER_LIST_ACTION_BAN_REASON.toTranslationMessage());
					Plugin.logger.LogInfo("Kicked - target:%s - by:%s".formatted(player.playerName, playerRef.getUsername()));
				}
				case PlayerListActions.TP_HERE -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					var command = "tp %s %s".formatted(player.playerName, playerRef.getUsername());
					CommandManager.get().handleCommand(SystemCommandSender.create(playerRef), command);
				}
				case PlayerListActions.TP_THERE -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					var command = "tp %s %s".formatted(playerRef.getUsername(), player.playerName);
					CommandManager.get().handleCommand(SystemCommandSender.create(playerRef), command);
				}
				case PlayerListActions.WHITELIST_REMOVE -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					WhitelistProvider.removeWhitelist(player.playerId);
					Plugin.logger.LogInfo("Blacklisted - target:%s - by:%s".formatted(player.playerName, playerRef.getUsername()));
				}
				case PlayerListActions.WHITELIST_ADD -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					WhitelistProvider.addWhitelist(player.playerId);
					Plugin.logger.LogInfo("Whitelisted - target:%s - by:%s".formatted(player.playerName, playerRef.getUsername()));
				}
				case PlayerListActions.KICK -> {
					var player = getPlayer(players, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					var pRef = Universe.get().getPlayer(player.playerId);
					if (pRef == null) break;
					pRef.getPacketHandler().disconnect(TranslationCode.UI_FEATURE_PLAYER_LIST_ACTION_BAN_REASON.toTranslationMessage());
					Plugin.logger.LogInfo("Kicked - target:%s - by:%s".formatted(player.playerName, playerRef.getUsername()));
				}
			}
			return true;
		}));

		return builder.build();
	}

	private static @Nullable PlayerListItem getPlayer(List<PlayerListItem> players, String playerId) {
		return players.stream().filter(x -> Objects.equals(x.playerId.toString(), playerId)).findFirst().orElse(null);
	}

	private static List<PlayerListItem> getPlayersList() {
		var playerList = new ArrayList<PlayerListItem>();
		for (var player : Universe.get().getPlayers()) {
			var playerId = player.getUuid();
			var isBanned = BanProvider.isPlayerBaned(playerId);
			var isWhiteListed = WhitelistProvider.isWhitelisted(playerId);
			playerList.add(new PlayerListItem(playerId, player.getUsername(), true, isBanned, isWhiteListed));
		}
		var bannedFilePlayers = BanProvider.getBannedPlayerIds();
		for (var playerId : bannedFilePlayers) {
			if (playerList.stream().anyMatch(x -> x.playerId.equals(playerId))) {
				continue;
			}
			var playerFile = PlayerFileProvider.getPlayer(playerId);
			var playerName = playerFile == null ? "???" : playerFile.Components().Nameplate().Text();
			var isWhiteListed = WhitelistProvider.isWhitelisted(playerId);
			playerList.add(new PlayerListItem(playerId, playerName, false, true, isWhiteListed));
		}
		var whitelistedPlayers = WhitelistProvider.getAllPlayerIds();
		for (var playerId : whitelistedPlayers) {
			try {
				if (playerList.stream().anyMatch(x -> x.playerId.equals(playerId))) {
					continue;
				}
				var playerFile = PlayerFileProvider.getPlayer(playerId);
				var playerName = playerFile == null ? "???" : playerFile.Components().Nameplate().Text();
				var isBanned = BanProvider.isPlayerBaned(playerId);
				playerList.add(new PlayerListItem(playerId, playerName, false, isBanned, true));
			} catch (Exception _) {
			}
		}
		return playerList;
	}
}
