package com.deezmods.unifiedui.extension.admin.essentials.models;

import java.util.UUID;

public record BanFileItem(
	UUID target,
	UUID By
) {
}
