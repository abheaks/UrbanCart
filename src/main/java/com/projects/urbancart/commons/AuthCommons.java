package com.projects.urbancart.commons;


import com.projects.urbancart.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {
    private RestTemplate restTemplate;

    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String tokenValue) {
        //Call the userService to validate the token.

        ResponseEntity<UserDto> responseEntity =
                restTemplate.getForEntity(
                        "http://localhost:8080/users/validate/" + tokenValue,
                        UserDto.class
                );

        if (responseEntity.getBody() == null) {
            //token is invalid.
            //Throw some exception here.
            return null;
        }

        return responseEntity.getBody();
    }
}