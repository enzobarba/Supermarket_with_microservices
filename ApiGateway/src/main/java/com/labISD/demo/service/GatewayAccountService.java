package com.labISD.demo.service;

import com.labISD.demo.dto.*;
import com.labISD.demo.enums.HTTP_METHOD_TYPE;
import com.labISD.demo.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayAccountService {

    private static final String BASE_URL = "http://account:9090";

    private static final String REGISTER_ACCOUNT_URL = BASE_URL + "/registerAccount";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String LOGOUT_URL = BASE_URL + "/logout";
    private static final String GET_ALL_ACCOUNTS_URL = BASE_URL + "/getAllAccounts";

    @Autowired
    private RestHelper restHelper;

    public String registerAccount(NewAccountDTO registerAccountDTO) {
        return restHelper.makeRequest(REGISTER_ACCOUNT_URL, HTTP_METHOD_TYPE.POST, registerAccountDTO, String.class, null);
    }

    public String login(LoginDTO loginDTO) {
        return restHelper.makeRequest(LOGIN_URL, HTTP_METHOD_TYPE.POST, loginDTO, String.class, null);
    }

    public String logout(String token) {
        return restHelper.makeRequest(LOGOUT_URL, HTTP_METHOD_TYPE.POST, token, String.class, null);
    }

    public String getAllAccounts(String token) {
        return restHelper.executeAuthorized(token, "getAllAccounts", GET_ALL_ACCOUNTS_URL, HTTP_METHOD_TYPE.GET, null);
    }
}