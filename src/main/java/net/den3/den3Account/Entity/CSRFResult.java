package net.den3.den3Account.Entity;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Optional;

public enum CSRFResult{
    ERROR_PARAMETER,ERROR_EQUAL_CSRF,ERROR_VERIFICATION,SUCCESS;
    private DecodedJWT decodedJWT;
    public Optional<DecodedJWT> getJWT(){
        return Optional.ofNullable(decodedJWT);
    }

    public CSRFResult setJWT(DecodedJWT jwt){
        this.decodedJWT = jwt;
        return this;
    }
}
