package coffeemachine.moneychecker;

import coffeemachine.checker.MoneyChecker;
import coffeemachine.objects.Money;
import coffeemachine.objects.Order;

public class AlwaysEnoughMoney implements MoneyChecker
{
    public Money getDifference(Order order) {
        return new Money(0);
    }
}
