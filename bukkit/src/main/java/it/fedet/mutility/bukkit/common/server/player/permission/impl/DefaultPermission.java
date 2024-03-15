package it.fedet.mutility.common.server.player.permission.impl;

import it.fedet.mutility.common.server.player.permission.PermissionProvider;

public enum DefaultPermission implements PermissionProvider {

    ADMIN("admin"),
    STAFF("staff"),
    DISGUISE("disguise"),
    VIP("vip"),
    USER("user");

    private final String permission;

    DefaultPermission(String permission) {
        this.permission = PERMISSION_PREFIX + permission;
    }

    @Override
    public String get() {
        return permission;
    }

}