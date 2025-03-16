package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.RegisterAccountDTO;
import com.labISD.demo.dto.RegisterProfileDTO;
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

    public String registerAccount(RegisterAccountDTO registerAccountDTO) {
        String username = registerAccountDTO.username();
        String passwd = registerAccountDTO.passwd();
        String inviteCode = registerAccountDTO.inviteCode();
        if (!username.matches("[a-zA-Z]{1}[a-zA-Z0-9]{2,29}"))
            throw new IllegalArgumentException();
        if (!passwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            throw new IllegalArgumentException();
        if (accountRepository.findByUsername(username) != null)
            throw new IllegalArgumentException();
        final String hash = SCryptUtil.scrypt(passwd, 32768, 8, 1);
        UUID id = UUID.randomUUID();
        ROLE role = null;
        if(inviteCode == null){
            role = ROLE.purchaser;
        }
        else if(inviteCode == "ADMIN"){
            role = ROLE.admin;
        }
        else if(inviteCode == "SUPPLYER"){
            role = ROLE.supplyer;
        }
        Account newAccount = new Account(id, username, hash, role);
        accountRepository.save(newAccount);
        createProfile(new RegisterProfileDTO(id, registerAccountDTO.name(), registerAccountDTO.surname(), registerAccountDTO.email()));     
        createCart(id);
        return "Account successfully created";
    }

    private void createProfile(RegisterProfileDTO registerProfileDTO) {
        webClientBuilder.build()
            .post().uri("http://profile:9091/createProfile")
            .bodyValue(registerProfileDTO)
            .retrieve().toBodilessEntity().block(); 
    }

    private void createCart(UUID userId) {
        webClientBuilder.build()
            .post().uri("http://cart:9094/createCart")
            .bodyValue(userId)
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
