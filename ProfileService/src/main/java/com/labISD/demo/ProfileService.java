package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    public boolean signIn(UUID id, String name, String surname, String email){
        if(profileRepository.findByEmail(email) != null){
            return false;
        }
        Profile profile = new Profile(id, name, surname, email);
        profileRepository.save(profile);
        return true;
    }

    //TO DO: ADD CALL BY DELETEACCOUNT IN ACCOUNTSERVICE
    public void deleteProfile(UUID id){
        profileRepository.deleteById(id);
    }

    public String getAllProfiles(){
        List <Profile> profiles = profileRepository.findAll();
        if(profiles.size() == 0){
            return "no profiles";
        }
        return profiles.toString();
    }

    //NEEDED ? 
    public Profile getProfile(UUID id){
        //RETURN TOSTRING()?
        return profileRepository.findById(id).orElse(null);
    }

}
