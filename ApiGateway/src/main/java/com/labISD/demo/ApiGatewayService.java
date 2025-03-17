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
        return sendRegistration(registerAccountDTO);
    }

    public String getAllAccounts(){
        return webClientBuilder.build()
        .get()
        .uri("http://account:9090/getAllAccounts")
        .retrieve().bodyToMono(String.class).block(); 
    }
 
    private String sendRegistration(RegisterAccountDTO registerAccountDTO) {
        return webClientBuilder.build()
            .post().uri("http://account:9090/registerAccount")
            .bodyValue(registerAccountDTO)
            .retrieve().bodyToMono(String.class).block(); 
    } 
}
