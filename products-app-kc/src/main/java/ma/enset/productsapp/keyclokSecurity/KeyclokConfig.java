package ma.enset.productsapp.keyclokSecurity;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyclokConfig {
    // cette methode permet spring pour prendre en consederation les config de keyclok dans application.properties
    @Bean
    public KeycloakSpringBootConfigResolver keyclok() {
        return new KeycloakSpringBootConfigResolver();
    }
    @Bean
    public KeycloakRestTemplate keycloakRestTemplate (KeycloakClientRequestFactory clientRequestFactory){
        return new KeycloakRestTemplate(clientRequestFactory);
    }
}
