package com.labISD.demo;

import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;


public class ClientApplication {

    private final WebClient webClient;

    public ClientApplication() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }


    public String sendRegistration(RegisterAccountDTO registerAccountDTO) {
 
        return webClient.post()
                        .uri("/account/registerAccount")  
                        .bodyValue(registerAccountDTO)    
                        .retrieve()                     
                        .bodyToMono(String.class)         
                        .block();                         
    }

    public String getAllAccounts() {
        return webClient.get()
                        .uri("/account/getAllAccounts")  
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String login(LoginDTO loginDTO) {
        return webClient.post()
                        .uri("/account/login")  
                        .bodyValue(loginDTO)
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public static void main(String[] args) {

        ClientApplication client = new ClientApplication();
        RegisterAccountDTO registerDTO = new RegisterAccountDTO("testUser", "Password123!", "test@example.com", "Enzo", "Barba", null);
        String registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);
        String accountsResponse = client.getAllAccounts();
        System.out.println("Risposta da getAllAccounts: " + accountsResponse);
        String token = client.login(new LoginDTO("testUser", "Password123!"));
        System.out.println("Risposta da login: " + token);
    }
}
