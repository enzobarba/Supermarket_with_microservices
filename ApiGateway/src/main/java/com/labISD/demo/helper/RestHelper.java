package com.labISD.demo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.RequestDTO;
import com.labISD.demo.enums.HTTP_METHOD_TYPE;
import java.util.UUID;

@Component
public class RestHelper {

    private static final String BASE_URL = "http://account:9090";

    private static final String CHECK_TOKEN_URL = BASE_URL + "/checkToken";
    private static final String GET_USER_BY_TOKEN_URL = BASE_URL + "/getUserByToken";
    private static final String GET_USER_ID_BY_TOKEN_URL = BASE_URL + "/getUserIdByToken";
    private static final String CHECK_REQUEST_URL = BASE_URL + "/checkRequest";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public <T, R> R makeRequest(String url, HTTP_METHOD_TYPE method, T body, Class<R> responseType, String token) {
        WebClient webClient = webClientBuilder.build();
        WebClient.RequestHeadersSpec<?> requestSpec;

        switch (method) {
            case GET:
                requestSpec = webClient.get().uri(url).header("Authorization", token);
                break;
            case POST:
                requestSpec = webClient.post().uri(url).bodyValue(body);
                break;
            case PUT:
                requestSpec = webClient.put().uri(url).bodyValue(body);
                break;
            case DELETE:
                requestSpec = webClient.delete().uri(url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        return requestSpec.retrieve().bodyToMono(responseType).block();
    }

    public UUID getUserIdByToken(String token) {
        return makeRequest(GET_USER_ID_BY_TOKEN_URL, HTTP_METHOD_TYPE.GET, null, UUID.class, token);
    }

    public String withUserId(String endpoint, String token) {
        UUID userId = getUserIdByToken(token);
        return String.format(endpoint, userId);
    }

    public String executeAuthorized(String token, String action, String url, HTTP_METHOD_TYPE method, Object body) {
        if (!checkToken(token)) {
            return "Authentication ERROR: token not valid";
        }
        if (!checkRequest(token, action)) {
            return String.format("Authorization ERROR: [%s] operation NOT ALLOWED", action);
        }
        return makeRequest(url, method, body, String.class, token);
    }

    private boolean checkToken(String token) {
        return makeRequest(CHECK_TOKEN_URL, HTTP_METHOD_TYPE.GET, null, Boolean.class, token);
    }

    private boolean checkRequest(String token, String request) {
        String username = makeRequest(GET_USER_BY_TOKEN_URL, HTTP_METHOD_TYPE.GET, null, String.class, token);
        RequestDTO requestDTO = new RequestDTO(username, request);
        return makeRequest(CHECK_REQUEST_URL, HTTP_METHOD_TYPE.POST, requestDTO, Boolean.class, null);
    }
}