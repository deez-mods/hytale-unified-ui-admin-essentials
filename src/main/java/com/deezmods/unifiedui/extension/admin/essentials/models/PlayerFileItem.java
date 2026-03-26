package com.deezmods.unifiedui.extension.admin.essentials.models;

public record PlayerFileItem(
	ComponentsItem Components
) {
	public record ComponentsItem(
		NameplateItem Nameplate
	) {
		public record NameplateItem(
			String Text
		) {
		}
	}
}


