package com.labISD.demo;

import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;
import com.labISD.demo.enums.*;


public class ClientApplication {

    private final WebClient webClient;

    public ClientApplication() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }


    public String sendRegistration(NewAccountDTO registerAccountDTO) {
        return webClient.post()
                        .uri("/account/registerAccount")  
                        .bodyValue(registerAccountDTO)    
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

    public String addProduct(NewProductDTO productDTO) {
        return webClient.post()
                        .uri("/product/addProduct")  
                        .bodyValue(productDTO)
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String getAllAccounts(String token) {
        return webClient.post()
                        .uri("/account/getAllAccounts")  
                        .bodyValue(token)
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String getAllProducts(String token) {
        return webClient.post()
                        .uri("/product/getAllProducts")  
                        .bodyValue(token)
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public static void main(String[] args) {

        ClientApplication client = new ClientApplication();
        NewAccountDTO registerDTO = new NewAccountDTO("supplier", "Password123!", "test1@example.com", "Enzo", "Barba", "SUPPLIER");
        String registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);
        registerDTO = new NewAccountDTO("admin", "Password123!", "test2@example.com", "Enzo", "Barba", "ADMIN");
        registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);
        registerDTO = new NewAccountDTO("purchaser", "Password123!", "test3@example.com", "Enzo", "Barba", null);
        registrationResponse = client.sendRegistration(registerDTO);
        System.out.println("Risposta dalla registrazione: " + registrationResponse);

        String token = client.login(new LoginDTO("supplier", "Password123!"));
        System.out.println("Risposta da login: " + token);
        String addProductResponse = client.addProduct(new NewProductDTO(token,"pollo", 2, 22, 1, CATEGORY.Meat));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllAccounts: "+client.getAllAccounts(token));
        System.out.println("Risposta da getAllProducts: "+client.getAllProducts(token));

        token = client.login(new LoginDTO("admin", "Password123!"));
        System.out.println("Risposta da login: " + token);
        addProductResponse = client.addProduct(new NewProductDTO(token,"pane", 2, 22, 1, CATEGORY.Bread));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllAccounts: "+client.getAllAccounts(token));
        System.out.println("Risposta da getAllProducts: "+client.getAllProducts(token));


        addProductResponse = client.addProduct(new NewProductDTO("fakeToken","pizza", 2, 22, 1, CATEGORY.Bread));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllAccounts: "+client.getAllAccounts("fakeToken"));
        System.out.println("Risposta da getAllProducts: "+client.getAllProducts("FakeToken"));



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
        System.out.println("Risposta da getAllProducts: " + client.getAllProducts());
        */

    }
}
