package com.labISD.demo.service;

import com.labISD.demo.dto.*;
import com.labISD.demo.enums.CATEGORY;
import com.labISD.demo.enums.HTTP_METHOD_TYPE;
import com.labISD.demo.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class GatewayProductService {

    private static final String BASE_URL = "http://product:9091";

    private static final String ADD_PRODUCT_URL = BASE_URL + "/addProduct";
    private static final String GET_ALL_PRODUCTS_URL = BASE_URL + "/getAllProducts";
    private static final String GET_SORTED_PRODUCTS_BY_RATING_DESC_URL = BASE_URL + "/getSortedProductsByRatingDesc";
    private static final String GET_PRODUCTS_BY_CATEGORY_URL = BASE_URL + "/getProductsByCategory?category=%s";
    private static final String SUPPLY_PRODUCT_URL = BASE_URL + "/supplyProduct";
    private static final String RATE_PRODUCT_URL = BASE_URL + "/rateProduct";
    private static final String GET_CART_URL = BASE_URL + "/getCart?userId=%s";
    private static final String GET_ORDERS_URL = BASE_URL + "/getOrders?userId=%s";
    private static final String CLEAR_CART_URL = BASE_URL + "/clearCart?userId=%s";
    private static final String ADD_ITEM_TO_CART_URL = BASE_URL + "/addItemToCart?userId=%s";
    private static final String REMOVE_ITEM_FROM_CART_URL = BASE_URL + "/removeItemFromCart?userId=%s&productName=%s";
    private static final String CHECKOUT_URL = BASE_URL + "/checkout?userId=%s";

    @Autowired
    private RestHelper restHelper;

    public String addProduct(String token, NewProductDTO productDTO) {
        return restHelper.executeAuthorized(token, "addProduct", ADD_PRODUCT_URL, HTTP_METHOD_TYPE.POST, productDTO);
    }

    public String getAllProducts(String token) {
        return restHelper.executeAuthorized(token, "getAllProducts", GET_ALL_PRODUCTS_URL, HTTP_METHOD_TYPE.GET, null);
    }

    public String getSortedProductsByRatingDesc(String token) {
        return restHelper.executeAuthorized(token, "getSortedProductsByRatingDesc", GET_SORTED_PRODUCTS_BY_RATING_DESC_URL, HTTP_METHOD_TYPE.GET, null);
    }

    public String getProductsByCategory(String token, CATEGORY category) {
        String url = String.format(GET_PRODUCTS_BY_CATEGORY_URL, category.name());
        return restHelper.executeAuthorized(token, "getProductsByCategory", url, HTTP_METHOD_TYPE.GET, null);
    }

    public String supplyProduct(String token, SupplyProductDTO supplyProductDTO) {
        return restHelper.executeAuthorized(token, "supplyProduct", SUPPLY_PRODUCT_URL, HTTP_METHOD_TYPE.PUT, supplyProductDTO);
    }

    public String rateProduct(String token, RateProductDTO rateProductDTO) {
        return restHelper.executeAuthorized(token, "rateProduct", RATE_PRODUCT_URL, HTTP_METHOD_TYPE.PUT, rateProductDTO);
    }

    public String getCart(String token) {
        return restHelper.executeAuthorized(token, "getCart",
                restHelper.withUserId(GET_CART_URL, token), HTTP_METHOD_TYPE.GET, null);
    }

    public String getOrders(String token) {
        return restHelper.executeAuthorized(token, "getOrders",
                restHelper.withUserId(GET_ORDERS_URL, token), HTTP_METHOD_TYPE.GET, null);
    }

    public String clearCart(String token) {
        return restHelper.executeAuthorized(token, "clearCart",
                restHelper.withUserId(CLEAR_CART_URL, token), HTTP_METHOD_TYPE.DELETE, null);
    }

    public String addItemToCart(String token, NewCartItemDTO newCartItemDTO) {
        return restHelper.executeAuthorized(token, "addItemToCart",
                restHelper.withUserId(ADD_ITEM_TO_CART_URL, token), HTTP_METHOD_TYPE.POST, newCartItemDTO);
    }

    public String removeItemFromCart(String token, String productName) {
        UUID userId = restHelper.getUserIdByToken(token);
        String url = String.format(REMOVE_ITEM_FROM_CART_URL, userId, productName);
        return restHelper.executeAuthorized(token, "removeItemFromCart", url, HTTP_METHOD_TYPE.DELETE, null);
    }

    public String checkout(String token, String cardNumber) {
        return restHelper.executeAuthorized(token, "checkout",
                restHelper.withUserId(CHECKOUT_URL, token), HTTP_METHOD_TYPE.POST, cardNumber);
    }

}