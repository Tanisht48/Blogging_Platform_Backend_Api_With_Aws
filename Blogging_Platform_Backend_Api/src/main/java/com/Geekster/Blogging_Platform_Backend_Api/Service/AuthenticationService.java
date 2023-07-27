package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.AuthenticationToken;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IAuthenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private IAuthenRepo authenticationRepo;
    public void saveAuthToken(AuthenticationToken authToken) {
        authenticationRepo.save(authToken);
    }

    public boolean authenticate(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getBlogger().getBloggerEmail();

        return tokenConnectedEmail.equals(email);
    }
}
