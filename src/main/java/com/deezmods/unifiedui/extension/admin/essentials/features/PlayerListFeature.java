package com.deezmods.unifiedui.extension.admin.essentials.features;

import com.deezmods.unifiedui.api.definitions.FeatureDefinition;
import com.deezmods.unifiedui.api.definitions.FeatureEventDefinition;
import com.deezmods.unifiedui.extension.admin.essentials.Plugin;
import com.deezmods.unifiedui.extension.admin.essentials.enums.ExtensionId;
import com.deezmods.unifiedui.extension.admin.essentials.enums.Permissions;
import com.deezmods.unifiedui.extension.admin.essentials.enums.PlayerListActions;
import com.deezmods.unifiedui.extension.admin.essentials.enums.TranslationCode;
import com.deezmods.unifiedui.extension.admin.essentials.models.PlayerListItem;
import com.deezmods.unifiedui.extension.admin.essentials.ui.PlayerListFeatureController;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.modules.accesscontrol.ban.InfiniteBan;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PlayerListFeature {
	private static final PlayerListFeatureController playerListFeatureController = new PlayerListFeatureController("");

	public static FeatureDefinition create(PlayerRef playerRef) {
		var banProvider = Plugin.banProvider;
		var playerList = new ArrayList<PlayerListItem>();

		var builder = new FeatureDefinition.Builder(
			ExtensionId.PLAYER_LIST.id,
			TranslationCode.UI_FEATURE_PLAYER_LIST_MENU_NAME.code
		);
		builder.withPermissionFilters(List.of(Permissions.PLAYER_LIST.id));
		builder.withBuildUserInterface((uiBuilder) -> CompletableFuture.supplyAsync(() -> {
			playerList.clear();
			for (var player : Universe.get().getPlayers()) {
				var playerId = player.getUuid();
				var isBanned = banProvider.hasBan(playerId);
				playerList.add(new PlayerListItem(playerId, player.getUsername(), isBanned));
			}
			playerListFeatureController.build(uiBuilder, playerList);
			return null;
		}));

		builder.withGetEventDefinitions(() -> CompletableFuture.supplyAsync(() -> {
			var events = new ArrayList<FeatureEventDefinition>();

			for (var index = 0; index < playerList.size(); index++) {
				var player = playerList.get(index);

				var banSelector = "#PlayerListContainer[%s] %s".formatted(index, player.isBanned() ? "#ActionButtonUnbanPlayer" : "#ActionButtonBanPlayer");
				var banAction = player.isBanned() ? PlayerListActions.PLAYER_UNBAN.id : PlayerListActions.PLAYER_BAN.id;
				var playerBanEvent = new FeatureEventDefinition(
					banSelector,
					banAction,
					CustomUIEventBindingType.Activating,
					Map.of(0, player.playerId().toString()),
					Collections.emptyMap()
				);

				events.add(playerBanEvent);
			}
			return events;
		}));

		builder.withHandleEvent((actionName, actionArgs, actionData) -> CompletableFuture.supplyAsync(() -> {
			PlayerListActions action = PlayerListActions.fromId(actionName);
			if (action == null) return false;
			switch (action) {
				case PlayerListActions.PLAYER_UNBAN -> {
					var player = getPlayer(playerList, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					banProvider.modify(uuids -> {
						uuids.remove(player.playerId());
						return true;
					});
				}
				case PlayerListActions.PLAYER_BAN -> {
					var player = getPlayer(playerList, actionArgs.getOrDefault(0, null));
					if (player == null) break;
					var ban = new InfiniteBan(player.playerId(),
						playerRef.getUuid(),
						Instant.now(),
						TranslationCode.UI_FEATURE_PLAYER_LIST_ACTION_BAN_REASON.toTranslationString());
					banProvider.modify(uuids -> {
						uuids.put(player.playerId(), ban);
						return true;
					});
				}
			}
			return true;
		}));

		return builder.build();
	}

	private static @Nullable PlayerListItem getPlayer(List<PlayerListItem> players, String playerId) {
		return players.stream().filter(x -> Objects.equals(x.playerId().toString(), playerId)).findFirst().orElse(null);
	}
}
