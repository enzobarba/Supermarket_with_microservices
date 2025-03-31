package com.labISD.demo.Authorization;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Reader {
    private static final int MAXPERM = 15;
    private static final int MAXROLE = 3;
    private Properties prop = new Properties();
    private List<Role> roles = new ArrayList<>();
    private List<Permission> perms = new ArrayList<>();

    @Autowired
    public Reader(){
        readFile();
    }

    public void readFile() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.prop")) {
            if (input == null) {
                throw new IOException("File db.prop not found in resources!");
            }
            prop.load(input);
            readPerms();
            readRoles();
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Role> getRoles(){
        return this.roles;
    }

    private String[] getPropValues(String prefix, int i, String ext2) {
        final String ext1 = ".name";
        String[] result = new String[2];
        result[0] = prop.getProperty(prefix + i + ext1);
        result[1] = prop.getProperty(prefix + i + ext2);
        return result;
    }

    private void readPerms() {
        for (int i = 1; i <= MAXPERM; i++) {
            String[] permId = getPropValues("perm", i, ".obj");
            if (permId[0] != null && permId[1] != null)
                perms.add(new Permission(permId[0], permId[1]));
        }
    }

    private void readRoles() {
        for (int i = 1; i <= MAXROLE; i++) {
            String[] roleNamePerm = getPropValues("role", i, ".perms");
            if (roleNamePerm[0] != null && roleNamePerm[1] != null) {
                String[] indexes = roleNamePerm[1].split(",");
                List<Permission> partPerm = new ArrayList<>();
                for (int j = 0; j < indexes.length; j++) {
                    int l = Integer.valueOf(indexes[j]) - 1;
                    partPerm.add(perms.get(l));
                }
                roles.add(new Role(roleNamePerm[0], partPerm));
            }
        }
    }

}

