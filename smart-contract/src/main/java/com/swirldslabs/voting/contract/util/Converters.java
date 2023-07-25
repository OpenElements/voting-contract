package com.swirldslabs.voting.contract.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Converters {

    private static final int COIN_SCALE = 18;

    public static BigInteger coinToUint(final BigDecimal count) {
        return count.scaleByPowerOfTen(COIN_SCALE).toBigInteger();
    }

    public static BigInteger coinToUint(final int count) {
        return coinToUint(BigDecimal.valueOf(count));
    }

    public static BigDecimal coinFromUint(final BigInteger unit) {
        return new BigDecimal(unit).scaleByPowerOfTen(-COIN_SCALE);
    }

    private Converters() {}
}
