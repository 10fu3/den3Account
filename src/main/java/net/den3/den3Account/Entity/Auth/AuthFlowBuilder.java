package net.den3.den3Account.Entity.Auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AuthFlowBuilder {

    private AuthFlow af = new AuthFlow();
    
    public static AuthFlowBuilder create(){
        return new AuthFlowBuilder();
    }
    
    public IAuthFlow build(){
        return af;
    }

    public AuthFlowBuilder setClientID(String clientID) {
        if(clientID == null){
            throw new NullPointerException();
        }
        this.af.clientID = clientID;
        return this;
    }

    public AuthFlowBuilder setAccountID(String accountID) {
        if(accountID == null){
            throw new NullPointerException();
        }
        this.af.accountID = accountID;
        return this;
    }

    public AuthFlowBuilder setNonce(String nonce) {
        if(nonce == null){
            throw new NullPointerException();
        }
        this.af.nonce = nonce;
        return this;
    }

    public AuthFlowBuilder setAuthorizationCode(String authorizationCode) {
        if(authorizationCode == null){
            throw new NullPointerException();
        }
        this.af.authorizationCode = authorizationCode;
        return this;
    }

    public AuthFlowBuilder setAcceptID(String acceptID) {
        if(acceptID== null){
            throw new NullPointerException();
        }
        this.af.acceptID = acceptID;
        return this;
    }

    public AuthFlowBuilder setFlowCode(String flowCode) {
        if(flowCode == null){
            throw new NullPointerException();
        }
        this.af.flowCode = flowCode;
        return this;
    }

    public AuthFlowBuilder setAccessToken(String accessToken) {
        if(accessToken == null){
            throw new NullPointerException();
        }
        this.af.accessToken = accessToken;
        return this;
    }

    public AuthFlowBuilder setRefreshToken(String refreshToken) {
        if(refreshToken == null){
            throw new NullPointerException();
        }
        this.af.refreshToken = refreshToken;
        return this;
    }

    public AuthFlowBuilder setLifeTimeAccessToken(String unixTime) {
        if(unixTime == null){
            throw new NullPointerException();
        }
        this.af.lifeTimeAccessToken = Long.parseLong(unixTime);
        return this;
    }

    public AuthFlowBuilder setLifeTimeRefreshToken(String unixTime) {
        if(unixTime == null){
            throw new NullPointerException();
        }
        this.af.lifeTimeRefreshToken = Long.parseLong(unixTime);
        return this;
    }

    public AuthFlowBuilder setState(String state){
        if(state == null){
            throw new NullPointerException();
        }
        for (int i = 0; i < AuthFlowState.values().length; i++) {
            if(state.equalsIgnoreCase(AuthFlowState.values()[i].name)){
                this.af.state = AuthFlowState.values()[i];
            }
        }
        throw new IllegalArgumentException();
    }
}

