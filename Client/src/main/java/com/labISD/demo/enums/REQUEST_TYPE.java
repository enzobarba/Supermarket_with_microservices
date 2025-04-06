package com.labISD.demo.enums;

public enum REQUEST_TYPE {
    REGISTER_ACCOUNT,
    LOGIN,
    LOGOUT,
    GET_ALL_ACCOUNTS,
    ADD_CARD_TO_ACCOUNT,
    GET_USER_CARDS,
    ADD_PRODUCT,
    GET_ALL_PRODUCTS,
    GET_SORTED_PRODUCTS_BY_RATING_DESC,
    GET_PRODUCTS_BY_CATEGORY,
    SUPPLY_PRODUCT,
    RATE_PRODUCT,
    GET_CART,
    GET_ORDERS,
    CLEAR_CART,
    ADD_ITEM_TO_CART,
    REMOVE_ITEM_FROM_CART,
    CHECKOUT;

    public String toCamelCase() {
        String enumName = this.name(); 
        String[] words = enumName.split("_");
        StringBuilder camelCaseString = new StringBuilder(words[0].toLowerCase());
        for (int i = 1; i < words.length; i++) {
            camelCaseString.append(words[i].substring(0, 1).toUpperCase())
                           .append(words[i].substring(1).toLowerCase());
        }
        return camelCaseString.toString();
    }
}
