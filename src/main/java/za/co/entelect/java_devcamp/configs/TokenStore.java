package za.co.entelect.java_devcamp.configs;

import org.springframework.stereotype.Component;

@Component
public class TokenStore {
    private volatile String token;


    public synchronized void setToken(String token) {
        this.token = token;
    }

    public synchronized String getToken() {
        if (token == null ) {
            return null;
        }
        return token;
    }

}
