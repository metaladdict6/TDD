import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


class AmountSpec {
    @Test
    void givenSameAmountWhenEqualsThenTrue() {
        Amount a = new Amount(2.0);
        Amount b = new Amount(2.0);
        assertTrue(a.equals(b));
    }

    @Test
    void whenAddAmountThenCorrectTotal() {
        Amount a = new Amount(2.0);
        Amount b = new Amount(3.0);
        Amount sum = a.add(b);
        assertEquals(new Amount(5.0), sum);
    }

    @Test
    void givenDifferentCurrencyWhenEqualsThenFalse() {
        Amount a = new Amount(2.0, "EUR");
        Amount b = new Amount(2.0, "USD");
        assertFalse(a.equals(b));
    }

    @Test
    void whenAddAmountThenCorrectCurrecy() {
        Amount a = new Amount(2.0, "USD");
        Amount b = new Amount(3.0, "USD");
        Amount sum = a.add(b);
        assertEquals(new Amount(5.0, "USD"), sum);
    }

    @Test
    void whenAddAmountThenConvertCurrency() {
        Amount a = new Amount(2.0, "EUR");
        Amount b = new Amount(3.0, "USD");
        Amount sum = a.add(b, Collections.singletonMap("EUR/USD", 1.2));
        assertEquals(new Amount(4.5, "EUR"), sum);
    }

    @Test
    void givenReverseWhenAddAmountThenConvertCurrency() {
        Amount a = new Amount(2.0, "EUR");
        Amount b = new Amount(3.0, "USD");
        Amount sum = a.add(b, Collections.singletonMap("USD/EUR", 1.2));
        assertEquals(new Amount(5.6, "EUR"), sum);
    }

    @Test
    void givenIncovertibleWhenAddAmountThenException() {
        Amount a = new Amount(2.0, "EUR");
        Amount b = new Amount(3.0, "USD");
        assertThrows(IllegalArgumentException.class,
                () -> a.add(b));
    }
}