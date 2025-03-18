package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    public void signIn(UUID id, String name, String surname, String email){
        Profile profile = new Profile(id, name, surname, email);
        profileRepository.save(profile);
    }

    public void deleteProfile(UUID id){
        profileRepository.deleteById(id);
    }

    public List <Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    public Profile getProfile(UUID id){
        return profileRepository.findById(id).orElse(null);
    }

}
