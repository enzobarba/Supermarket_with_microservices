package com.labISD.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.ProfileRequest;
import com.labISD.demo.service.ProfileService;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/createProfile")
    public void createProfile(@RequestBody ProfileRequest profileRequest) {
        profileService.signIn(profileRequest.getUuid(), profileRequest.getName(), profileRequest.getSurname(), profileRequest.getEmail());
    }

    @GetMapping("/getProfiles")
    public String getProfiles(){
        return profileService.getAllProfile().toString();
   }
    
}
