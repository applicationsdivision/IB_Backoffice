package com.backoffice.BackofficeInternetBanking.Service;



import com.backoffice.BackofficeInternetBanking.dao.UserRepository;
import com.backoffice.BackofficeInternetBanking.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {



    private final String authUrl = "http://172.16.21.24:4045/ldap/api/v1/authentication";

    public ResponseEntity<String> authenticateUser(String username,String password){
        RestTemplate restTemplate=new RestTemplate();
        String user = "bnr\\" + username;

        AuthenticationRequest authRequest=new AuthenticationRequest(user,password);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequest> entity= new HttpEntity<>(authRequest,headers);

        return restTemplate.exchange(authUrl, HttpMethod.POST, entity,String.class);
    }
}
