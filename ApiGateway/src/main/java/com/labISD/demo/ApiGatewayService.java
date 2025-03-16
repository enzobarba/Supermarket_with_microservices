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

    private String sendRegistration(RegisterAccountDTO registerAccountDTO) {
        return webClientBuilder.build()
            .post().uri("http://account:9090/createAccount")
            .bodyValue(registerAccountDTO)
            .retrieve().bodyToMono(String.class).block(); 
    } 
}
