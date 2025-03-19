package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.RegisterProfileDTO;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/createProfile")
    public boolean createProfile(@RequestBody RegisterProfileDTO profileRequestDTO) {
        return profileService.signIn(profileRequestDTO.id(), profileRequestDTO.name(), profileRequestDTO.surname(), profileRequestDTO.email());
    }

    @GetMapping("/getAllProfiles")
    public String getAllProfiles(){
        return profileService.getAllProfiles();
   }
    
}
