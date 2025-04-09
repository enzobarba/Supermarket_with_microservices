package com.labISD.demo;

import com.labISD.demo.dto.*;
import com.labISD.demo.enums.*;


public class ClientApplication {

    private ClientFacade clientFacade;

    public ClientApplication(){
        this.clientFacade = new ClientFacade();
    }

    public static void main(String[] args) {
        ClientApplication client = new ClientApplication();

        client.clientFacade.populateAccounts();

        client.clientFacade.populateAccountsWithErrors();

        //error login: username not valid
        String purchaserToken = client.clientFacade.request(REQUEST_TYPE.LOGIN, "enzo270",
                        new LoginDTO("enzo270","Ids2025!"), null, null);

        //error login: password not valid
        purchaserToken = client.clientFacade.request(REQUEST_TYPE.LOGIN, "enzo2709",
                        new LoginDTO("enzo2709","Ids2025"), null, null);

        purchaserToken = client.clientFacade.request(REQUEST_TYPE.LOGIN, "enzo2709",
                        new LoginDTO("enzo2709","Ids2025!"), null, null);

        String supplierToken = client.clientFacade.request(REQUEST_TYPE.LOGIN, "aMidolo",
                        new LoginDTO("aMidolo","Ids2025!"), null, null);
        
        String adminToken = client.clientFacade.request(REQUEST_TYPE.LOGIN, "eTramontana",
                        new LoginDTO("eTramontana","Ids2025!"), null, null);
        
        client.clientFacade.request(REQUEST_TYPE.GET_ALL_ACCOUNTS, "eTramontana", null, adminToken, null);

        client.clientFacade.getAllAccountsWithErrors(purchaserToken, supplierToken);

        client.clientFacade.populateProducts(supplierToken);

        client.clientFacade.populateProductsWithErrors(adminToken, purchaserToken, supplierToken);
        
        //client.clientFacade.request(REQUEST_TYPE.SUPPLY_PRODUCT, "aMidolo",
          //              new SupplyProductDTO("spaghetti",30), supplierToken, null);

        client.clientFacade.request(REQUEST_TYPE.GET_ALL_PRODUCTS, "enzo2709",
                        null, purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.GET_PRODUCTS_BY_CATEGORY, "enzo2709",
                        null, purchaserToken, CATEGORY.Pasta.name());

        //CHECK BELOW
        client.clientFacade.request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                        new NewCartItemDTO("gamberoni",11), purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                        new NewCartItemDTO("gamberoni",11), purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.ADD_CARD_TO_ACCOUNT, "enzo2709",
                        new NewCreditCardDTO("1111000011110000", "MASTERCARD","03/29",100), purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.CHECKOUT, "enzo2709",
                        "1111000011110000", purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.GET_CART, "enzo2709",
                        null, purchaserToken, null);
        
        client.clientFacade.request(REQUEST_TYPE.GET_ALL_PRODUCTS, "enzo2709",
                        null, purchaserToken, null);

        client.clientFacade.request(REQUEST_TYPE.GET_USER_CARDS, "enzo2709",
                        null, purchaserToken, null);

    }
}
