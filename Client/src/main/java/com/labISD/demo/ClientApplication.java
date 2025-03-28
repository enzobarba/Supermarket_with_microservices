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

    public String addProduct(ProductDTO productDTO) {
        return webClient.post()
                        .uri("/product/addProduct")  
                        .bodyValue(productDTO)
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String getAllProducts() {
        return webClient.get()
                        .uri("/product/getAllProducts")  
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String checkRequest(RequestDTO requestDTO) {
        return webClient.post()
                        .uri("/account/checkRequest").bodyValue(requestDTO)  
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }


    public static void main(String[] args) {

        ClientApplication client = new ClientApplication();
        RegisterAccountDTO registerDTO = new RegisterAccountDTO("testUser1", "Password123!", "test1@example.com", "Enzo", "Barba", "SUPPLIER");
        String registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);
        registerDTO = new RegisterAccountDTO("testUser2", "Password123!", "test2@example.com", "Enzo", "Barba", "ADMIN");
        registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);
        registerDTO = new RegisterAccountDTO("testUser3", "Password123!", "test3@example.com", "Enzo", "Barba", null);
        registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);

        /* 
        RequestDTO requestDTO = new RequestDTO("testUser1", "getAllAccounts");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser1", "addProduct");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser1", "getAllProducts");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser1", "supplyProduct");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser1", "getCart");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser1", "addCardToAccount");
        System.out.println(client.checkRequest(requestDTO));

        requestDTO = new RequestDTO("testUser2", "getAllAccounts");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser2", "addProduct");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser2", "getAllProducts");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser2", "supplyProduct");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser2", "getCart");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser2", "addCardToAccount");
        System.out.println(client.checkRequest(requestDTO));

        requestDTO = new RequestDTO("testUser3", "getAllAccounts");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser3", "addProduct");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser3", "getAllProducts");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser3", "supplyProduct");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser3", "getCart");
        System.out.println(client.checkRequest(requestDTO));
        requestDTO = new RequestDTO("testUser3", "addCardToAccount");
        System.out.println(client.checkRequest(requestDTO));
        */
        
        /* 
        String accountsResponse = client.getAllAccounts();
        System.out.println("Risposta da getAllAccounts: " + accountsResponse);
        String token = client.login(new LoginDTO("testUser", "Password123!"));
        System.out.println("Risposta da login: " + token);
        String addProductResponse = client.addProduct(new ProductDTO("pollo", 2, 22, 1, CATEGORY.Meat));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllProducts: " + client.getAllProducts());
        */

    }
}
