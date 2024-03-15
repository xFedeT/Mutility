package it.fedet.mutility.velocity.common.server.permission.impl;

import it.fedet.mutility.velocity.common.server.permission.PermissionProvider;

public enum DefaultPermission implements PermissionProvider {

    ADMIN("admin"),
    STAFF("staff"),
    GLOBALCHAT("globalchat"),
    HOST("host"),
    DISGUISE("disguise"),
    VIP("vip"),
    USER("user");

    private final String permission;

    DefaultPermission(String permission) {
        this.permission = PREFIX + permission;
    }

    @Override
    public String get() {
        return permission;
    }

}