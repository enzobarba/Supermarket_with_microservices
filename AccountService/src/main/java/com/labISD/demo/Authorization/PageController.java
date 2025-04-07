package com.labISD.demo.authorization;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.labISD.demo.enums.ROLE;


@Service
public class PageController {
    private Reader db;

    @Autowired
    public PageController(Reader db) {
        this.db = db;
    }

    public boolean requestOp(ROLE role, String request) {
        Role r = getRole(role);
        return (r != null && r.hasPermission(request));
    }

    private Role getRole(ROLE role){
        String roleString = role.toString();
        List <Role> roles = db.getRoles();
        for(Role r: roles){
            if(r.name().equals(roleString)){
                return r;
            }
        }
        return null;
    }
 
    
}