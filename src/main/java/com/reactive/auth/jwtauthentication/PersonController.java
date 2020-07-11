package com.reactive.auth.jwtauthentication;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("person")
public class PersonController {

    @GetMapping("/v1")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Person> getPersonv1(Mono<Principal> principal) {
         return principal
                .map(Principal::getName)
                .map(name -> new Person(1l,name));
    }

    @GetMapping("/v2")
    public Mono<String> getPersonv2() {
        return Mono.just("Hello");
    }
}
