package dev.anurag.JournalApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private static String SECRET_KEY;

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public static void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }
}
