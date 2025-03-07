package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.ProfileRequest;
import com.labISD.demo.Authentication.TokenGenerator;
import com.labISD.demo.Authentication.TokenStore;
import com.labISD.demo.Authentication.Token;
import com.lambdaworks.crypto.SCryptUtil;
import com.labISD.demo.enums.ROLE;
import java.util.UUID;
import java.util.List;


@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final TokenStore tokenStore;
    private final TokenGenerator tokenGenerator;
    private WebClient.Builder webClientBuilder;

    @Autowired
    public AccountService(AccountRepository accountRepository, TokenStore tokenStore, TokenGenerator tokenGenerator, WebClient.Builder webClientBuilder) {
        this.accountRepository = accountRepository;
        this.tokenStore = tokenStore;
        this.tokenGenerator = tokenGenerator;
        this.webClientBuilder = webClientBuilder;
    }

    public void registerAccount(final String username, final String passwd, final String email, final String name, final String surname) {
        registerAccount(username, passwd, email, name, surname, null); 
    }

    public void registerAccount(final String username, final String passwd, final String email, final String name, final String surname, String inviteCode) {
        if (!username.matches("[a-zA-Z]{1}[a-zA-Z0-9]{2,29}"))
            throw new IllegalArgumentException();
        if (!passwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            throw new IllegalArgumentException();
        if (accountRepository.findByUsername(username) != null)
            throw new IllegalArgumentException();
        final String hash = SCryptUtil.scrypt(passwd, 32768, 8, 1);
        UUID uuid = UUID.randomUUID();
        ROLE role = null;
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
        createProfile(uuid, name, surname, email);     
    }

    public void createProfile(UUID uuid, String name, String surname, String email) {
        webClientBuilder.build()
            .post().uri("http://localhost:8080/createProfile")
            .bodyValue(new ProfileRequest(uuid, name, surname, email))
            .retrieve().toBodilessEntity().block(); 
    }

    public String logIn(String user, String pwd) {
        Account account = accountRepository.findByUsername(user);
        if (null == account)
            return null;
        String authenticationInfo = account.getHashedPassword();
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

    public List <Account> getAllAccounts(){
        return accountRepository.findAll();
    }
}
