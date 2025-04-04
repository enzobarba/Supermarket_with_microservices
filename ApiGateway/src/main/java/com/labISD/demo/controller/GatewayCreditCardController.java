package com.labISD.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.NewCreditCardDTO;
import com.labISD.demo.service.GatewayCreditCardService;


@RestController
@RequestMapping("/creditCard")
public class GatewayCreditCardController {

    @Autowired
    GatewayCreditCardService gatewayCreditCardService;

    @PostMapping("/addCardToAccount")
    public String addCardToAccount(@RequestHeader("Authorization") String token, @RequestBody NewCreditCardDTO newCreditCardDTO){
        return gatewayCreditCardService.addCardToAccount(token, newCreditCardDTO);
    }

    @GetMapping("/getUserCards")
    public String getUserCards(@RequestHeader("Authorization") String token){
        return gatewayCreditCardService.getUserCards(token);
    }
}
