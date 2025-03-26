package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;
import com.labISD.demo.Authentication.*;
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
        if (accountRepository.findByUsername(username) != null)
            return "Error: Username already exists.";
        //redundant constraint (also in entity)
        if (!username.matches("[a-zA-Z]{1}[a-zA-Z0-9]{2,29}"))
            return "Error: Invalid username format. Username must start with a letter and be between 3 and 30 characters.";
        if (!passwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            return "Error: Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character.";
        if(accountRepository.findByEmail(registerAccountDTO.email()) != null){
            return "Email already in use.";
        }
        final String hash = SCryptUtil.scrypt(passwd, 32768, 8, 1);
        ROLE role = getRole(inviteCode);
        if(role == null){
            return "Error: invalid invite code.";
        }
        Account newAccount = new Account(registerAccountDTO.username(), hash, role, registerAccountDTO.name(), registerAccountDTO.surname(), registerAccountDTO.email());
        accountRepository.save(newAccount);
        createCart(newAccount.getId());
        return "Account successfully created";
    }

    private ROLE getRole(String inviteCode){
        ROLE role = null;
        if(inviteCode == null){
            role = ROLE.purchaser;
        }
        else if(inviteCode.equals("ADMIN")){
            role = ROLE.admin;
        }
        else if(inviteCode.equals("SUPPLYER")){
            role = ROLE.supplyer;
        }
        return role;

    }

    private void createCart(UUID userId) {
        webClientBuilder.build()
            .get().uri("http://product:9091/createCart?userId="+userId)
            .retrieve().toBodilessEntity().block(); 
    }

    //TO DO: FIX RETURN VALUE
    public String login(LoginDTO loginDTO) {
        Account account = accountRepository.findByUsername(loginDTO.username());
        if (null == account)
            return "Error: Invalid credentials.";
        String authenticationInfo = account.getHashedPassword();
        if (SCryptUtil.check(loginDTO.password(), authenticationInfo)) {
            Token token = tokenGenerator.tokenBuild(loginDTO.username());
            tokenStore.store(token);
            return token.payload();
        }
        return "Error: Invalid credentials.";
    }

    public boolean checkToken(String tokenPayload){
        boolean validToken = false;
        if(tokenStore.isPresent(tokenPayload) && tokenStore.isValidSignature(tokenPayload)){
            validToken = true;
        }
        return validToken;
    }

    public String getAllAccounts(){
        List <Account> accounts = accountRepository.findAll();
        if(accounts.size() == 0){
            return "No accounts";
        }
        return accounts.toString();
    }
}
