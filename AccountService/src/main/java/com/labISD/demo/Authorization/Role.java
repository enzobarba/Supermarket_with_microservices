package com.labISD.demo.Authorization;

import java.util.List;

public class Role {
    private final String idRole;
    private final List<Permission> ps;

    public Role(String name, List<Permission> permis) {
        idRole = name;
        ps = permis;
    }

    public boolean hasPermission(String p) {
        return ps.stream().anyMatch(perm -> perm.idPerm().equals(p));
    }

    public String name() {
        return idRole;
    }
}
