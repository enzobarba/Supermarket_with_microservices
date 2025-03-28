package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;

@Service
public class ApiGatewayService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String registerAccount(RegisterAccountDTO registerAccountDTO){
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

    public String getAllAccounts(){
        return webClientBuilder.build()
        .get()
        .uri("http://account:9090/getAllAccounts")
        .retrieve().bodyToMono(String.class).block(); 
    }
    
    public String checkRequest(RequestDTO requestDTO){
        boolean permission = webClientBuilder.build()
        .post().uri("http://account:9090/checkRequest")
        .bodyValue(requestDTO)
        .retrieve().bodyToMono(Boolean.class).block(); 
        if(permission == true){
            return String.format("Operation %s requested by %s: ALLOWED",requestDTO.request(), requestDTO.username());
        }
        return String.format("Operation %s requested by %s: DENIED",requestDTO.request(), requestDTO.username());
    }

    public String addProduct(ProductDTO productDTO){
        return webClientBuilder.build()
        .post()
        .uri("http://product:9091/addProduct").bodyValue(productDTO)
        .retrieve().bodyToMono(String.class).block(); 
    }

    public String getAllProducts(){
        return webClientBuilder.build()
        .get()
        .uri("http://product:9091/getAllProducts")
        .retrieve().bodyToMono(String.class).block(); 
    }

   
}
