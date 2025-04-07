package com.labISD.demo;

import com.labISD.demo.dto.*;
import com.labISD.demo.enums.*;


public class ClientApplication {

    private ClientDispatcher clientDispatcher;

    public ClientApplication(){
        this.clientDispatcher = new ClientDispatcher();
    }

    public static void main(String[] args) {
        ClientApplication client = new ClientApplication();

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.REGISTER_ACCOUNT, 
        new NewAccountDTO("enzo2709", "adSss23!", "enzo1334@gmail.com", "Enzo", "Barba", null), null, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.REGISTER_ACCOUNT, 
        new NewAccountDTO("enzoSuppl", "adSss23!", "enzo1335@gmail.com", "Enzo", "Barba", "SUPPLIER"), null, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.REGISTER_ACCOUNT, 
        new NewAccountDTO("enzoAdmin", "adSss23!", "enzo1336@gmail.com", "Enzo", "Barba", "ADMIN"), null, null));

        String tokenP = client.clientDispatcher.request(REQUEST_TYPE.LOGIN, 
                        new LoginDTO("enzo2709","adSss23!"), null, null);

        String tokenS = client.clientDispatcher.request(REQUEST_TYPE.LOGIN, 
                        new LoginDTO("enzoSuppl","adSss23!"), null, null);
        
        String tokenA = client.clientDispatcher.request(REQUEST_TYPE.LOGIN, 
                        new LoginDTO("enzoAdmin","adSss23!"), null, null);
        
        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.ADD_PRODUCT,
                        new NewProductDTO("spaghetti",1,20,2,CATEGORY.Pasta), tokenS, null));
        
        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.SUPPLY_PRODUCT,
                        new SupplyProductDTO("spaghetti",30), tokenS, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.ADD_PRODUCT,
                        new NewProductDTO("fusilli",1,20,2,CATEGORY.Pasta), tokenS, null));
        
        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.ADD_PRODUCT,
                        new NewProductDTO("gamberoni",1,20,2,CATEGORY.Frozen), tokenS, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.GET_ALL_PRODUCTS,
                        null, tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.ADD_ITEM_TO_CART,
                        new NewCartItemDTO("gamberoni",11), tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.ADD_ITEM_TO_CART,
                        new NewCartItemDTO("gamberoni",11), tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.GET_CART,
                        null, tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.ADD_CARD_TO_ACCOUNT,
                        new NewCreditCardDTO("1111000011110000", "MASTERCARD","03/29",100), tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.CHECKOUT,
                        "1111000011110000", tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.GET_CART,
                        null, tokenP, null));
        
        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.GET_ALL_PRODUCTS,
                        null, tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.GET_USER_CARDS,
                        null, tokenP, null));

        System.out.println(client.clientDispatcher.request(REQUEST_TYPE.GET_ALL_ACCOUNTS, null, tokenA, null));
    }
}
