package com.labISD.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.labISD.demo.domain.Account;
import com.labISD.demo.repository.AccountRepository;
import com.labISD.demo.service.Authentication.TokenGenerator;
import com.labISD.demo.service.Authentication.TokenStore;
import com.labISD.demo.service.Authentication.Token;
import com.lambdaworks.crypto.SCryptUtil;
import com.labISD.demo.enums.ROLE;
import java.util.UUID;


@Service
public class AccountService {
    
   private final AccountRepository accountRepository;
    private final TokenStore tokenStore;
    private final TokenGenerator tokenGenerator;

    @Autowired
    public AccountService(AccountRepository accountRepository, TokenStore tokenStore, TokenGenerator tokenGenerator) {
        this.accountRepository = accountRepository;
        this.tokenStore = tokenStore;
        this.tokenGenerator = tokenGenerator;
    }

    public void registerUser(final String username, final String passwd, final String email, final String name, final String surname) {
        registerUser(username, passwd, email, name, surname, null); 
    }

    public void registerUser(final String username, final String passwd, final String email, final String name, final String surname, String inviteCode) {
        if (!username.matches("[a-zA-Z]{1}[a-zA-Z0-9]{2,29}"))
            throw new IllegalArgumentException();
        if (!passwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            throw new IllegalArgumentException();
        if (accountRepository.findByUsername(username) == null)
            throw new IllegalArgumentException();
        final String hash = SCryptUtil.scrypt(passwd, 32768, 8, 1);
        UUID uuid = UUID.randomUUID();
        ROLE role = null;
        //fix clear invitation code
        if(inviteCode == null){
            role = ROLE.purchaser;
        }
        if(inviteCode == "ADMIN"){
            role = ROLE.admin;
        }
        else if(inviteCode == "SUPPLYER"){
            role = ROLE.supplyer;
        }
        Account newAccount = new Account(uuid, username, hash, role);
        accountRepository.save(newAccount);
        //to do: add call api to profileService (createProfile)
    }

    public String logIn(String user, String pwd) {
        Account account = accountRepository.findByUsername(user);
        if (null == account)
            return null;
        String authenticationInfo = account.getUsername();
        if (SCryptUtil.check(pwd, authenticationInfo)) {
            Token token = tokenGenerator.tokenBuild(user);
            tokenStore.store(token);
            return token.payload();
        }
        return null;
    }

    public boolean checkToken(String tokenPayload){
        boolean validToken = false;
        if(tokenStore.isPresent(tokenPayload) && tokenStore.isValidSignature(tokenPayload)){
            validToken = true;
        }
        return validToken;
    }
}
