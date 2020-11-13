package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.stream.IntStream;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal bigA = new BigDecimal(a);
        BigDecimal bigB = new BigDecimal(b);
        BigDecimal result = bigA.divide(bigB, 2, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        return BigInteger.valueOf(IntStream.range(2, 10_000_000).filter(this::isPrimary).skip(range + 1).findFirst().getAsInt());
    }

    public boolean isPrimary(int number) {
        for (int i = 2; i < number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
