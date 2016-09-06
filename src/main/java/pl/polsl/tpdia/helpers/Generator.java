package pl.polsl.tpdia.helpers;

import java.security.SecureRandom;

/**
 * Objects generator
 */
public abstract class Generator<T> {
    SecureRandom random;

    Generator(SecureRandom random) {
        this.random = random;
    }

    public abstract T generate();
}
