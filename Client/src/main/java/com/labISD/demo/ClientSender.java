package com.labISD.demo;

import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.enums.HTTP_METHOD_TYPE;
import com.labISD.demo.enums.REQUEST_TYPE;

public class ClientSender {
    
    private final WebClient webClient;
    private static final String BASE_URL = "http://localhost:8080";

    private static final String PATH_PRODUCT_URL = "/product/";
    private static final String PATH_ACCOUNT_URL = "/account/";
    private static final String PATH_CREDITCARD_URL = "/creditCard/";

    public ClientSender() {
        WebClient.Builder builder = WebClient.builder();
        this.webClient = builder.baseUrl(BASE_URL).build();
    }
    
    public <T> String request(REQUEST_TYPE sendRequestType, T body, String token, String param){
        String sendRequest = sendRequestType.toCamelCase();
        switch(sendRequest){
            case "registerAccount":
            case "login":
                return sendRequest(PATH_ACCOUNT_URL+sendRequest, HTTP_METHOD_TYPE.POST, body, null);
            case "logout":
                return sendRequest(PATH_ACCOUNT_URL+sendRequest, HTTP_METHOD_TYPE.POST, null, token);
            case "getAllAccounts":
                return sendRequest(PATH_ACCOUNT_URL+sendRequest, HTTP_METHOD_TYPE.GET, null, token);
            case "addCardToAccount":
                return sendRequest(PATH_CREDITCARD_URL+sendRequest, HTTP_METHOD_TYPE.POST, body, token);
            case "getUserCards":
                return sendRequest(PATH_CREDITCARD_URL+sendRequest, HTTP_METHOD_TYPE.GET, null, token);
            case "addProduct":
            case "addItemToCart":
            case "checkout":
                return sendRequest(PATH_PRODUCT_URL+sendRequest, HTTP_METHOD_TYPE.POST, body, token);
            case "getAllProducts":
            case "getSortedProductsByRatingDesc":
            case "getCart":
            case "getOrders":
                return sendRequest(PATH_PRODUCT_URL+sendRequest, HTTP_METHOD_TYPE.GET, null, token);
            case "getProductsByCategory":
                return sendRequest(PATH_PRODUCT_URL+sendRequest+"?category="+param, HTTP_METHOD_TYPE.GET, null, token);
            case "rateProduct":
            case "supplyProduct":
                return sendRequest(PATH_PRODUCT_URL+sendRequest, HTTP_METHOD_TYPE.PUT, body, token);
            case "clearCart":
                return sendRequest(PATH_PRODUCT_URL+sendRequest, HTTP_METHOD_TYPE.DELETE, null, token);
            case "removeItemFromCart":
                return sendRequest(PATH_PRODUCT_URL+sendRequest+"?productName="+param, HTTP_METHOD_TYPE.DELETE, null, token);
            default:
                return "ERROR: client sendRequest NOT VALID";
        }
    }

    private <T> String sendRequest(String url, HTTP_METHOD_TYPE method, T body, String token) {
        WebClient.RequestHeadersSpec<?> sendRequestSpec;
        switch (method) {
            case GET:
                sendRequestSpec = webClient.get().uri(url).header("Authorization", token);
                break;
            case POST:
                sendRequestSpec = webClient.post().uri(url).header("Authorization", token).bodyValue(body);
                break;
            case PUT:
                sendRequestSpec = webClient.put().uri(url).header("Authorization", token).bodyValue(body);
                break;
            case DELETE:
                sendRequestSpec = webClient.delete().uri(url).header("Authorization", token);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        return sendRequestSpec.retrieve().bodyToMono(String.class).block();
    }
    
}
