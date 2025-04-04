package com.labISD.demo.service;

import com.labISD.demo.dto.NewCreditCardDTO;
import com.labISD.demo.enums.HTTP_METHOD_TYPE;
import com.labISD.demo.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayCreditCardService {

    private static final String BASE_URL = "http://creditCard:9092";

    private static final String ADD_CARD_TO_ACCOUNT_URL = BASE_URL+"/addCardToAccount?userId=%s";
    private static final String GET_USER_CARDS_URL = BASE_URL+"/getUserCards?userId=%s";

    @Autowired
    private RestHelper restHelper;

    public String addCardToAccount(String token, NewCreditCardDTO newCreditCardDTO) {
        return restHelper.executeAuthorized(token, "addCardToAccount", restHelper.withUserId(ADD_CARD_TO_ACCOUNT_URL, token), HTTP_METHOD_TYPE.POST, newCreditCardDTO);
    }

    public String getUserCards(String token) {
        return restHelper.executeAuthorized(token, "getUserCards", restHelper.withUserId(GET_USER_CARDS_URL, token), HTTP_METHOD_TYPE.GET, null);
    }
}
