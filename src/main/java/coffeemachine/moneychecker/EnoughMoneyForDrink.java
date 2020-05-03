package coffeemachine.moneychecker;

import coffeemachine.checker.MoneyChecker;
import coffeemachine.interfaces.MoneyInterface;
import coffeemachine.objects.Money;
import coffeemachine.objects.Order;

public class EnoughMoneyForDrink implements MoneyChecker
{
    private MoneyInterface moneyInterface;

    public EnoughMoneyForDrink(MoneyInterface moneyInterface) {

        this.moneyInterface = moneyInterface;
    }

    public Money getDifference(Order order) {
        return new Money(
                moneyInterface.readMoney().getAmount() - order.getDrink().getPrice().getAmount()
        );
    }
}
