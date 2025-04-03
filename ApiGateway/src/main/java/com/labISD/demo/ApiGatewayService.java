package com.labISD.demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;
import com.labISD.demo.enums.CATEGORY;

@Service
public class ApiGatewayService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String registerAccount(NewAccountDTO registerAccountDTO){
        return makeRequest("http://account:9090/registerAccount", "POST", registerAccountDTO, String.class, null); 
    }

    public String login(LoginDTO loginDTO){
        return makeRequest("http://account:9090/login", "POST", loginDTO, String.class, null);
    }

    public String logout(String token){
        return makeRequest("http://account:9090/logout", "POST", token, String.class, null);
    }

    public String getAllAccounts(String token){
        return executeAuthAuthRequest(token, "getAllAccounts",
         "http://account:9090/getAllAccounts", "GET", null);
    }

    public String addProduct(String token, NewProductDTO productDTO){
        return executeAuthAuthRequest(token, "addProduct",
            "http://product:9091/addProduct", "POST", productDTO);
    }

    public String getAllProducts(String token){
        return executeAuthAuthRequest(token, "getAllProducts",
            "http://product:9091/getAllProducts", "GET", null);
    }

    public String getSortedProductsByRatingDesc(String token){
        return executeAuthAuthRequest(token, "getSortedProductsByRatingDesc",
            "http://product:9091/getSortedProductsByRatingDesc", "GET", null);
    }

    public String getProductsByCategory(String token, CATEGORY category){
        String url = String.format("http://product:9091/getProductsByCategory?category=%s", category.name());
        return executeAuthAuthRequest(token, "getProductsByCategory",
            url, "GET", null);
    }

    public String supplyProduct(String token, SupplyProductDTO supplyProductDTO){
        return executeAuthAuthRequest(token, "supplyProduct",
            "http://product:9091/supplyProduct", "PUT", supplyProductDTO);
    }

    public String rateProduct(String token, RateProductDTO rateProductDTO){
        return executeAuthAuthRequest(token, "rateProduct",
            "http://product:9091/rateProduct", "PUT", rateProductDTO);
    }

    //DA RIVEDERE SE SI PUO' EVITARE QUESTO PASSAGGIO INIZIALE, O FARE DIVERSAMENTE (COSA SUCCEDE SE TOKEN NON VALIDO) (TRAMITE USERNAME PASSATO A CREATE CART??)
    public String getCart(String token){
        UUID userId = getUserIdByToken(token);
        String url = String.format("http://product:9091/getCart?userId=%s",userId.toString());
        return executeAuthAuthRequest(token, "getCart",
            url, "GET", null);
    }

    public String getOrders(String token){
        UUID userId = getUserIdByToken(token);
        String url = String.format("http://product:9091/getOrders?userId=%s",userId.toString());
        return executeAuthAuthRequest(token, "getOrders",
            url, "GET", null);
    }

    public String clearCart(String token){
        UUID userId = getUserIdByToken(token);
        String url = "http://product:9091/clearCart?userId="+userId.toString();
        return executeAuthAuthRequest(token, "clearCart",
            url, "DELETE", null);
    }

    public String addItemToCart(String token, NewCartItemDTO newCartItemDTO){
        UUID userId = getUserIdByToken(token);
        String url = "http://product:9091/addItemToCart?userId="+userId.toString();
        return executeAuthAuthRequest(token, "addItemToCart",
            url, "POST", newCartItemDTO);
    }

    //da togliere nome prodotto in chiaro
    public String removeItemFromCart(String token, String productName){
        UUID userId = getUserIdByToken(token);
        String url = String.format("http://product:9091/removeItemFromCart?userId=%s&productName=%s", userId, productName);
        return executeAuthAuthRequest(token, "removeItemFromCart",
            url, "DELETE", null);
    }

    public String checkout(String token, String cardNumber){
        UUID userId = getUserIdByToken(token);
        String url = "http://product:9091/checkout?userId="+userId.toString();
        return executeAuthAuthRequest(token, "checkout",
            url, "POST", cardNumber);
    }

    private <T, R> R makeRequest(String url, String method, T body, Class<R> responseType, String token) {
        WebClient webClient = webClientBuilder.build();
        WebClient.RequestHeadersSpec<?> requestSpec; 
        if (method.equalsIgnoreCase("GET")) {
            requestSpec = webClient.get().uri(url).header("Authorization", token);
        } else {
            requestSpec = webClient.method(HttpMethod.valueOf(method.toUpperCase()))
                                   .uri(url)
                                   .bodyValue(body);
        }
        return requestSpec.retrieve().bodyToMono(responseType).block();
    }

    private String executeAuthAuthRequest(String token, String action, String url, String method, Object body) {
        String authResult = checkAuthAuth(token, action);
        if (!authResult.equals("OK")) {
            return authResult;
        }
        return makeRequest(url, method, body, String.class, token);
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
        return makeRequest("http://account:9090/checkToken", "GET", null, Boolean.class, token);
    }
    
    private boolean checkRequest(String token, String request){
        String username = getUserByToken(token);
        RequestDTO requestDTO = new RequestDTO(username, request);
        return makeRequest("http://account:9090/checkRequest", "POST", requestDTO, Boolean.class, null);
    }

    private String getUserByToken(String token){
        return makeRequest("http://account:9090/getUserByToken", "GET", null, String.class, token);
    }
        
    private UUID getUserIdByToken(String token){
        return makeRequest("http://account:9090/getUserIdByToken", "GET", null, UUID.class, token);
    }
}
