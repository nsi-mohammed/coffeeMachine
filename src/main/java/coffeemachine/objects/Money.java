package coffeemachine.objects;

public class Money
{
    private final int amount;

    public Money(int cents) {
        this.amount = cents;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return amount == money.amount;

    }

    @Override
    public int hashCode() {
        return amount;
    }

}
