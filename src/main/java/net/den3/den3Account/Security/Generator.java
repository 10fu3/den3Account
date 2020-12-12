package net.den3.den3Account.Security;

import com.auth0.jwt.algorithms.Algorithm;

@FunctionalInterface
public interface Generator <T> {
    String generate(T obj, Algorithm algorithm);
}
