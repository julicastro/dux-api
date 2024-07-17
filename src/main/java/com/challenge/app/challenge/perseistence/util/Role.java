package com.challenge.app.challenge.perseistence.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    USER(Arrays.asList(
            RolePermission.READ_ALL_PRODUCTS,
            RolePermission.READ_ONE_TEAM,
            RolePermission.CREATE_ONE_TEAM,
            RolePermission.UPDATE_ONE_TEAM,
            RolePermission.DELETE_ONE_TEAM
    ));

    private List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
