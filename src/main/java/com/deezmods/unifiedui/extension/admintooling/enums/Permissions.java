package com.deezmods.unifiedui.extension.admintooling.enums;

public enum Permissions
{
    BANLIST("banlist"),
    WHITELIST("whitelist");

    public final String id;

    Permissions(String id) {
        this.id = "com.deezmods.unifiedui.extension.admintooling.%s".formatted(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
