package net.ismailtosun.discordbotultimate.Services;

import net.ismailtosun.discordbotultimate.Entity.Token;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class TokenService {
    private static final long TOKEN_VALIDITY_PERIOD_MS = 18000000;
    private ArrayList<Token> tokens;

    public TokenService() {
        tokens = new ArrayList<>();
    }
    public String generateToken() {
        if (tokens.size() > 10) {
            tokens.remove(0);
        }

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().encodeToString(bytes);
        Token newToken = new Token(token, System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD_MS);
        tokens.add(newToken);
        return token;
    }

    public boolean isTokenValid(String token) {
        for (Token t : tokens) {
            if (t.getToken().equals(token)) {
                if (t.getExpirationDate() > System.currentTimeMillis()) {
                    return true;
                } else {
                    tokens.remove(t);
                    return false;
                }
            }
        }
        return false;

    }
}
