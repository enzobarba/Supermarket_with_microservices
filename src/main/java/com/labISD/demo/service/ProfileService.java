package com.labISD.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.labISD.demo.repository.ProfileRepository;
import com.labISD.demo.domain.Profile;
import java.util.Optional;
import java.util.List;
import java.util.UUID;


public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    public void signIn(UUID id, String name, String surname, float money){
        Profile profile = new Profile(id, name, surname, money);
        profileRepository.save(profile);
    }

    public void deleteProfile(UUID id){
        profileRepository.deleteById(id);
    }

    public List <Profile> getAllProfile(){
        return profileRepository.findAll();
    }

    public Profile getProfile(UUID id){
        Optional <Profile> profile = profileRepository.findById(id);
        return profile.get();
    }

}
