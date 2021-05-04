import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {

    public static final Money ZERO = of(BigDecimal.ZERO);

    private final BigDecimal value;

    Money(BigDecimal value) {
        this.value = value;
    }

    public static Money of(BigDecimal value) {
        return new Money(value.setScale(2, RoundingMode.HALF_UP));
    }

    public static Money of(String value) {
        return of(new BigDecimal(value));
    }

    public static Money min(Money money1, Money money2) {
        return of(money1.value.min(money2.value));
    }

    public static Money max(Money money1, Money money2) {
        return of(money1.value.max(money2.value));
    }

    public Money add(Money that) {
        return of(this.value.add(that.value));
    }

    public Money subtract(Money that) {
        return of(this.value.subtract(that.value));
    }

    public Money multiply(BigDecimal multiplicand) {
        return of(this.value.multiply(multiplicand));
    }

    public Money divide(BigDecimal divisor) {
        return of(this.value.divide(divisor, RoundingMode.HALF_UP));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Objects.toString(value);
    }

    public BigDecimal getValue() {
        return value;
    }
}