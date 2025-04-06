package com.labISD.demo;

import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.enums.HTTP_METHOD_TYPE;

public class ClientSender {
    
    private final WebClient webClient;
    private static final String BASE_URL = "http://localhost:8080";

    public ClientSender(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    } 

    public <T> String sendRequest(String url, HTTP_METHOD_TYPE method, T body, String token) {
        WebClient.RequestHeadersSpec<?> requestSpec;
        switch (method) {
            case GET:
                requestSpec = webClient.get().uri(url).header("Authorization", token);
                break;
            case POST:
                requestSpec = webClient.post().uri(url).header("Authorization", token).bodyValue(body);
                break;
            case PUT:
                requestSpec = webClient.put().uri(url).header("Authorization", token).bodyValue(body);
                break;
            case DELETE:
                requestSpec = webClient.delete().uri(url).header("Authorization", token);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        return requestSpec.retrieve().bodyToMono(String.class).block();
    }
    
}
