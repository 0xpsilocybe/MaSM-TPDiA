package pl.polsl.tpdia.helpers;

import java.security.SecureRandom;

/**
 * Generic enum random generator
 */
public class EnumGenerator<T extends Enum<T>> extends Generator<T> {
    private T[] values;
    private int size;

    public EnumGenerator(T enumType, SecureRandom random) {
        super(random);
        values = enumType.getDeclaringClass().getEnumConstants();
        size = values.length;
    }

    @Override
    public T generate() {
        int index = random.nextInt(size);
        return values[index];
    }
}
