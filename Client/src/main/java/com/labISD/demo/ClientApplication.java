package com.labISD.demo;

import java.util.Scanner;
import com.labISD.demo.dto.*;
import com.labISD.demo.enums.*;


public class ClientApplication {

    private static ClientFacade clientFacade = new ClientFacade();
    
    public static void main(String[] args) {

        clientFacade.populateAccounts();

        clientFacade.populateAccountsWithErrors();

        //error login: username not valid
        String purchaserToken1 = clientFacade.request(REQUEST_TYPE.LOGIN, "enzo270",
                        new LoginDTO("enzo270","Ids2025!"), null, null);

        //error login: password not valid
        purchaserToken1 = clientFacade.request(REQUEST_TYPE.LOGIN, "enzo2709",
                        new LoginDTO("enzo2709","Ids2025"), null, null);

        purchaserToken1 = clientFacade.request(REQUEST_TYPE.LOGIN, "enzo2709",
                        new LoginDTO("enzo2709","Ids2025!"), null, null);

        String purchaserToken2 = clientFacade.request(REQUEST_TYPE.LOGIN, "vincenzo2000",
                        new LoginDTO("vincenzo2000","Ids2025!"), null, null);

        String supplierToken = clientFacade.request(REQUEST_TYPE.LOGIN, "aMidolo",
                        new LoginDTO("aMidolo","Ids2025!"), null, null);
        
        String adminToken = clientFacade.request(REQUEST_TYPE.LOGIN, "eTramontana",
                        new LoginDTO("eTramontana","Ids2025!"), null, null);
        
        clientFacade.request(REQUEST_TYPE.GET_ALL_ACCOUNTS, "eTramontana", null, adminToken, null);

        clientFacade.getAllAccountsWithErrors(purchaserToken1, supplierToken);

        clientFacade.populateProducts(supplierToken);

        clientFacade.populateProductsWithErrors(adminToken, purchaserToken1, supplierToken);
        
        clientFacade.request(REQUEST_TYPE.GET_ALL_PRODUCTS, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.GET_PRODUCTS_BY_CATEGORY, "enzo2709",
                        null, purchaserToken1, CATEGORY.Pasta.name());



        Scanner scanner = new Scanner(System.in);
        System.out.println("Press a key to continue . . . ");
        scanner.nextLine();



        clientFacade.populateCart(purchaserToken1);

        clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.ADD_CARD_TO_ACCOUNT, "enzo2709",
                        new NewCreditCardDTO("1111000011110000", "MASTERCARD","03/29",100), purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.CHECKOUT, "enzo2709",
                        "1111000011110000", purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.GET_USER_CARDS, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.GET_ORDERS, "enzo2709",
                        null, purchaserToken1, null);
        
        clientFacade.request(REQUEST_TYPE.GET_ALL_PRODUCTS, "enzo2709",
                        null, purchaserToken1, null);


        

        System.out.println("Press a key to continue . . . ");
        scanner.nextLine();



        clientFacade.request(REQUEST_TYPE.SUPPLY_PRODUCT, "aMidolo",
                        new SupplyProductDTO("gamberoni",4), supplierToken, null);

        clientFacade.request(REQUEST_TYPE.GET_ALL_PRODUCTS, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                        new NewCartItemDTO("gamberoni",10), purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.CHECKOUT, "enzo2709",
                        "1111000011110000", purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.REMOVE_ITEM_FROM_CART, "enzo2709",
                        null, purchaserToken1, "gamberoni");

        clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken1, null);



        System.out.println("Press a key to continue . . . ");
        scanner.nextLine();



        clientFacade.request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                        new NewCartItemDTO("lattuga",30), purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.ADD_ITEM_TO_CART, "vincenzo2000",
                        new NewCartItemDTO("lattuga",20), purchaserToken2, null);

        clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken1, null);

        clientFacade.request(REQUEST_TYPE.GET_CART, "vincenzo2000",
                        null, purchaserToken2, null);

        //card not valid
        clientFacade.request(REQUEST_TYPE.CHECKOUT, "vincenzo2000",
                      "1111000011110000", purchaserToken2, null);

        clientFacade.request(REQUEST_TYPE.ADD_CARD_TO_ACCOUNT, "vincenzo2000",
                        new NewCreditCardDTO("1111111111111111", "MASTERCARD","03/29",100), purchaserToken2, null);

        clientFacade.request(REQUEST_TYPE.CHECKOUT, "vincenzo2000",
                        "1111111111111111", purchaserToken2, null);

        clientFacade.request(REQUEST_TYPE.GET_ORDERS, "vincenzo2000",
                        null, purchaserToken2, null);

        clientFacade.request(REQUEST_TYPE.CHECKOUT, "enzo2709",
                        "1111000011110000", purchaserToken1, null);
        
        scanner.close();
    }
}
