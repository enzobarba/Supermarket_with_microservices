package com.labISD.demo;

import com.labISD.demo.dto.NewAccountDTO;
import com.labISD.demo.dto.NewCartItemDTO;
import com.labISD.demo.dto.NewProductDTO;
import com.labISD.demo.enums.CATEGORY;
import com.labISD.demo.enums.REQUEST_TYPE;

public class ClientFacade {
    
    private final ClientSender clientSender;

    public ClientFacade() {
        this.clientSender = new ClientSender();
    }

    public <T,R> String request(REQUEST_TYPE requestType, String user, T body, String token, String param){
        String response = clientSender.request(requestType, body, token, param);
        /*try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String requestToPrint = String.format("User [%s]       Request [%s]", user, requestType.name());
        System.out.println(requestToPrint);
        /*try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String responseToPrint = String.format("Server response: \n%s\n\n", response);
        System.out.print(responseToPrint);
        if(requestType == REQUEST_TYPE.LOGIN){
            return response;
        }
        return null;
    }

    public void populateAccounts(){
        request(REQUEST_TYPE.REGISTER_ACCOUNT, "enzo2709", 
                new NewAccountDTO("enzo2709", "Ids2025!", "enzo1334@gmail.com", "Enzo",
                "Barba", null), null, null);

        request(REQUEST_TYPE.REGISTER_ACCOUNT, "vincenzo2000", 
                new NewAccountDTO("vincenzo2000", "Ids2025!", "vincenzo1334@gmail.com", "Vincenzo",
                "Barba", null), null, null);

        request(REQUEST_TYPE.REGISTER_ACCOUNT, "aMidolo",
                new NewAccountDTO("aMidolo", "Ids2025!", "aMidolo@gmail.com", "Alessandro",
                "Midolo", "SUPPLIER"), null, null);

        request(REQUEST_TYPE.REGISTER_ACCOUNT, "eTramontana",
                new NewAccountDTO("eTramontana", "Ids2025!", "eTramontana@gmail.com", "Emiliano",
                "Tramontana", "ADMIN"), null, null);
    }

    public void populateAccountsWithErrors(){
        //username already in use 
        request(REQUEST_TYPE.REGISTER_ACCOUNT, "enzo2709", 
                new NewAccountDTO("enzo2709", "Ids2025!", "enzoBarba@gmail.com",
                "Enzo", "Barba", null), null, null);

        //password not valid
        request(REQUEST_TYPE.REGISTER_ACCOUNT, "aMidolo44",
                new NewAccountDTO("aMidolo44", "ids", "aMidolo0000@gmail.com", "Alessandro",
                "Midolo", "SUPPLIER"), null, null);

        //email already in use
        request(REQUEST_TYPE.REGISTER_ACCOUNT, "eTramontana11",
            new NewAccountDTO("eTramontana11", "Ids2025!", "eTramontana@gmail.com", "Emiliano",
            "Tramontana", "ADMIN"), null, null);

        //invitecode not valid
        request(REQUEST_TYPE.REGISTER_ACCOUNT, "enzoBar", 
            new NewAccountDTO("enzoBar", "Ids2025!", "enzoBarba@gmail.com", "Enzo",
            "Barba", "LETMEINASADMIN"), null, null);
    }

    public void getAllAccountsWithErrors(String purchaserToken, String supplierToken){
        //not authorized
        request(REQUEST_TYPE.GET_ALL_ACCOUNTS, "enzo2709", null, purchaserToken, null);

        request(REQUEST_TYPE.GET_ALL_ACCOUNTS, "aMidolo", null, supplierToken, null);

        //not authenticated
        request(REQUEST_TYPE.GET_ALL_ACCOUNTS, "?", null, "fakeToken", null);
    }

    public void populateProducts(String supplierToken){
        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("spaghetti",1.20f,20,1,CATEGORY.Pasta), supplierToken, null);

        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("fusilli",1.40f,50,1,CATEGORY.Pasta), supplierToken, null);
        
        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("gamberoni",10,6,0.7f,CATEGORY.Frozen), supplierToken, null);

        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("mela x4",2.30f,54,1,CATEGORY.Fruit), supplierToken, null);

        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("acqua naturale x6",2.50f,120,12,CATEGORY.Drinks), supplierToken, null);

        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("lattuga",1.30f,43,0.3f,CATEGORY.Vegetables), supplierToken, null);

        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("rucola",1.50f,5,0.4f,CATEGORY.Vegetables), supplierToken, null);
    }

    public void populateProductsWithErrors(String adminToken, String purchaserToken, String supplierToken){
        //not authorized
        request(REQUEST_TYPE.ADD_PRODUCT, "eTramontana",
                new NewProductDTO("spaghetti",1.20f,20,1,CATEGORY.Pasta), adminToken, null);

        request(REQUEST_TYPE.ADD_PRODUCT, "enzo2709",
                new NewProductDTO("fusilli",1.40f,50,1,CATEGORY.Pasta), purchaserToken, null);
        
        //not authenticated
        request(REQUEST_TYPE.ADD_PRODUCT, "?",
                new NewProductDTO("gamberoni",10,6,0.7f,CATEGORY.Frozen), "FakeToken", null);

        //product already exists
        request(REQUEST_TYPE.ADD_PRODUCT, "aMidolo",
                new NewProductDTO("rucola",1.50f,5,0.4f,CATEGORY.Vegetables), supplierToken, null);

    }


    public void populateCart(String purchaserToken){

        request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                new NewCartItemDTO("spaghetti",5), purchaserToken, null);

        request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                new NewCartItemDTO("rucola",3), purchaserToken, null);

        //quantity not available
        request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                new NewCartItemDTO("gamberoni",10), purchaserToken, null);

        request(REQUEST_TYPE.ADD_ITEM_TO_CART, "enzo2709",
                new NewCartItemDTO("tovaglioli",10), purchaserToken, null);

    }

}
