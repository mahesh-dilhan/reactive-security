package com.reactive.auth.jwtauthentication;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Data
public class JwtProperties {
    //private String secretKey = "rzxlszyykpbgqcflzxsqcysyhljt";
    private String secretKey = UUID.randomUUID().toString();
    //validity in milliseconds
    private long validityInMs = 3600000; // 1h
}