package com.labISD.demo;

import org.springframework.web.reactive.function.client.WebClient;

import com.labISD.demo.enums.HTTP_METHOD_TYPE;
import com.labISD.demo.enums.REQUEST_TYPE;

public class ClientDispatcher {
    
    private final ClientSender clientSender;
    private static final String PATH_PRODUCT_URL = "/product/";
    private static final String PATH_ACCOUNT_URL = "/account/";
    private static final String PATH_CREDITCARD_URL = "/creditCard/";

    public ClientDispatcher() {
        WebClient.Builder builder = WebClient.builder();
        this.clientSender = new ClientSender(builder);
    }


    public <T> String request(REQUEST_TYPE requestType, T body, String token, String param){
        String request = requestType.toCamelCase();
        switch(request){
            case "registerAccount":
            case "login":
                return clientSender.sendRequest(PATH_ACCOUNT_URL+request, HTTP_METHOD_TYPE.POST, body, null);
            case "logout":
                return clientSender.sendRequest(PATH_ACCOUNT_URL+request, HTTP_METHOD_TYPE.POST, null, token);
            case "getAllAccounts":
                return clientSender.sendRequest(PATH_ACCOUNT_URL+request, HTTP_METHOD_TYPE.GET, null, token);
            case "addCardToAccount":
                return clientSender.sendRequest(PATH_CREDITCARD_URL+request, HTTP_METHOD_TYPE.POST, body, token);
            case "getUserCards":
                return clientSender.sendRequest(PATH_CREDITCARD_URL+request, HTTP_METHOD_TYPE.GET, null, token);
            case "addProduct":
            case "addItemToCart":
            case "checkout":
                return clientSender.sendRequest(PATH_PRODUCT_URL+request, HTTP_METHOD_TYPE.POST, body, token);
            case "getAllProducts":
            case "getSortedProductsByRatingDesc":
            case "getCart":
            case "getOrders":
                return clientSender.sendRequest(PATH_PRODUCT_URL+request, HTTP_METHOD_TYPE.GET, null, token);
            case "getProductsByCategory":
                return clientSender.sendRequest(PATH_PRODUCT_URL+request+"?category="+param, HTTP_METHOD_TYPE.GET, null, token);
            case "rateProduct":
            case "supplyProduct":
                return clientSender.sendRequest(PATH_PRODUCT_URL+request, HTTP_METHOD_TYPE.PUT, body, token);
            case "clearCart":
                return clientSender.sendRequest(PATH_PRODUCT_URL+request, HTTP_METHOD_TYPE.DELETE, null, token);
            case "removeItemFromCart":
                return clientSender.sendRequest(PATH_PRODUCT_URL+request+"?productName="+param, HTTP_METHOD_TYPE.DELETE, null, token);
            default:
                return "ERROR: client request NOT VALID";
        }
    }

}
