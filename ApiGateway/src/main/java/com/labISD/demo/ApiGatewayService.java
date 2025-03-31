package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;

@Service
public class ApiGatewayService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String registerAccount(NewAccountDTO registerAccountDTO){
        return webClientBuilder.build()
        .post().uri("http://account:9090/registerAccount")
        .bodyValue(registerAccountDTO)
        .retrieve().bodyToMono(String.class).block(); 
    }

    public String login(LoginDTO loginDTO){
        return webClientBuilder.build()
        .post().uri("http://account:9090/login")
        .bodyValue(loginDTO)
        .retrieve().bodyToMono(String.class).block(); 
    }

    public String addProduct(NewProductDTO productDTO){
        String checkAuthAuth = checkAuthAuth(productDTO.token(), "addProduct");
        if(!checkAuthAuth.equals("OK")){
            return checkAuthAuth;
        }
        return webClientBuilder.build()
        .post()
        .uri("http://product:9091/addProduct").bodyValue(productDTO)
        .retrieve().bodyToMono(String.class).block(); 
    }

    public String getAllAccounts(String token){
        String checkAuthAuth = checkAuthAuth(token, "getAllAccounts");
        if(!checkAuthAuth.equals("OK")){
            return checkAuthAuth;
        }
        return webClientBuilder.build()
        .get()
        .uri("http://account:9090/getAllAccounts")
        .retrieve().bodyToMono(String.class).block(); 
    }

    public String getAllProducts(String token){
        String checkAuthAuth = checkAuthAuth(token, "getAllProducts");
        if(!checkAuthAuth.equals("OK")){
            return checkAuthAuth;
        }
        return webClientBuilder.build()
        .get()
        .uri("http://product:9091/getAllProducts")
        .retrieve().bodyToMono(String.class).block(); 
    }

    private String checkAuthAuth(String token, String request){
        boolean authenticated = checkToken(token);
        if(!authenticated){
            return "Authentication ERROR: token not valid";
        }
        boolean authorized = checkRequest(token, request);
        if(!authorized){
            return String.format("Authorization ERROR: [%s] operation  NOT ALLOWED", request);
        }
        return "OK";
    }

    private boolean checkToken(String token){
        return webClientBuilder.build()
        .post().uri("http://account:9090/checkToken")
        .bodyValue(token)
        .retrieve().bodyToMono(Boolean.class).block(); 
    }
    
    private boolean checkRequest(String token, String request){
        String username = getUserByToken(token);
        RequestDTO requestDTO = new RequestDTO(username, request);
        return webClientBuilder.build()
        .post().uri("http://account:9090/checkRequest")
        .bodyValue(requestDTO)
        .retrieve().bodyToMono(Boolean.class).block(); 
    }

    private String getUserByToken(String token){
        return webClientBuilder.build()
        .post().uri("http://account:9090/getUserByToken")
        .bodyValue(token)
        .retrieve().bodyToMono(String.class).block(); 
    }

}
