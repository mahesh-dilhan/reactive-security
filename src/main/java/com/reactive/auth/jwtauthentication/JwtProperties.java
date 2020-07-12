package com.reactive.auth.jwtauthentication;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Data
public class JwtProperties {
    //secret key
    private String secretKey = UUID.randomUUID().toString();
    //validity in milliseconds
    private long validityInMs = 3600000; // 1h
}