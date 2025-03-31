package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;

@Service
public class ApiGatewayService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    private <T, R> R makeRequest(String url, String method, T body, Class<R> responseType) {
        WebClient webClient = webClientBuilder.build();
        WebClient.RequestHeadersSpec<?> requestSpec; 
        if (method.equalsIgnoreCase("GET")) {
            requestSpec = webClient.get().uri(url);
        } else {
            requestSpec = webClient.method(HttpMethod.valueOf(method.toUpperCase()))
                                   .uri(url)
                                   .bodyValue(body);
        }
    
        return requestSpec.retrieve().bodyToMono(responseType).block();
    }
    

    public String registerAccount(NewAccountDTO registerAccountDTO){
        return makeRequest("http://account:9090/registerAccount", "POST", registerAccountDTO, String.class); 
    }

    public String login(LoginDTO loginDTO){
        return makeRequest("http://account:9090/login", "POST", loginDTO, String.class);
    }

    public String addProduct(NewProductDTO productDTO){
        String checkAuthAuth = checkAuthAuth(productDTO.token(), "addProduct");
        if(!checkAuthAuth.equals("OK")){
            return checkAuthAuth;
        }
        return makeRequest("http://product:9091/addProduct", "POST", productDTO, String.class);
    }

    public String getAllAccounts(String token){
        String checkAuthAuth = checkAuthAuth(token, "getAllAccounts");
        if(!checkAuthAuth.equals("OK")){
            return checkAuthAuth;
        }
        return makeRequest("http://account:9090/getAllAccounts", "GET", token, String.class);
    }

    public String getAllProducts(String token){
        String checkAuthAuth = checkAuthAuth(token, "getAllProducts");
        if(!checkAuthAuth.equals("OK")){
            return checkAuthAuth;
        }
        return makeRequest("http://product:9091/getAllProducts", "GET", token, String.class);
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
        return makeRequest("http://account:9090/checkToken", "POST", token, Boolean.class);
    }
    
    private boolean checkRequest(String token, String request){
        String username = getUserByToken(token);
        RequestDTO requestDTO = new RequestDTO(username, request);
        return makeRequest("http://account:9090/checkRequest", "POST", requestDTO, Boolean.class);
    }

    private String getUserByToken(String token){
        return makeRequest("http://account:9090/getUserByToken", "POST", token, String.class);
    }

}
