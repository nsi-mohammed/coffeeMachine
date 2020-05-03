package coffeemachine.checker;

import coffeemachine.objects.Money;
import coffeemachine.objects.Order;

public interface MoneyChecker
{
    Money getDifference(Order order);
}
