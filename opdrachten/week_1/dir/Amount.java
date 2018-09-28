import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Amount {
    private double amount;
    private String currency;

    public Amount(double amount) {
        this(amount, "EUR");
    }

    public Amount(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Amount add(Amount addend) {
        return add(addend, Collections.EMPTY_MAP);
    }

    public Amount add(Amount addend, Map<String, Double> conversion) {
        if (currency != addend.currency) {
            String k = currency + "/" + addend.currency;
            String r = addend.currency + "/" + currency;
            double c = conversion.getOrDefault(k,
                    1/conversion.getOrDefault(r, 0.0));
            if (!Double.isFinite(c)) {
                throw new IllegalArgumentException("Incovertible");
            }
            return new Amount(amount + addend.amount / c, currency);
        }
        return new Amount(amount + addend.amount, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Double.compare(amount1.amount, amount) == 0 &&
                Objects.equals(currency, amount1.currency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(amount, currency);
    }
}