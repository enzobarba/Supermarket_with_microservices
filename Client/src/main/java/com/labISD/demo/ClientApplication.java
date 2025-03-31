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

    public String addProduct(String token, NewProductDTO productDTO) {
        return webClient.post()
                        .uri("/product/addProduct")  
                        .header("Authorization", token) 
                        .bodyValue(productDTO)
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String getAllAccounts(String token) {
        return webClient.get()
                        .uri("/account/getAllAccounts")  
                        .header("Authorization", token) 
                        .retrieve()                    
                        .bodyToMono(String.class)       
                        .block();                      
    }

    public String getAllProducts(String token) {
        return webClient.get()
                        .uri("/product/getAllProducts")  
                        .header("Authorization", token) 
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
        String addProductResponse = client.addProduct(token, new NewProductDTO("pollo", 2, 22, 1, CATEGORY.Meat));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllAccounts: "+client.getAllAccounts(token));
        System.out.println("Risposta da getAllProducts: "+client.getAllProducts(token));

        token = client.login(new LoginDTO("admin", "Password123!"));
        System.out.println("Risposta da login: " + token);
        addProductResponse = client.addProduct(token, new NewProductDTO("pollo", 2, 22, 1, CATEGORY.Meat));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllAccounts: "+client.getAllAccounts(token));
        System.out.println("Risposta da getAllProducts: "+client.getAllProducts(token));


        addProductResponse = client.addProduct("fakeToken", new NewProductDTO("pollo", 2, 22, 1, CATEGORY.Meat));
        System.out.println("Risposta da addProduct: " + addProductResponse);
        System.out.println("Risposta da getAllAccounts: "+client.getAllAccounts("fakeToken"));
        System.out.println("Risposta da getAllProducts: "+client.getAllProducts("FakeToken"));


    }
}
