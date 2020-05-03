package coffeemachine;

import coffeemachine.adapter.MessageAdapter;
import coffeemachine.adapter.OrderAdapter;
import coffeemachine.checker.MoneyChecker;
import coffeemachine.objects.Money;
import coffeemachine.objects.Order;
import coffeemachine.objects.alerts.NotEnoughMoneyMessage;
import coffeemachine.checker.QuantityDrinkChecker;
import coffeemachine.interfaces.DrinkMaker;
import coffeemachine.interfaces.EmailNotifier;
import coffeemachine.interfaces.OrderInterface;

public class CoffeeMachine
{
    private final DrinkMaker drinkMaker;
    private final OrderInterface orderInterface;
    private final MoneyChecker moneyChecker;
    private final OrderAdapter orderAdapter;
    private final MessageAdapter messageAdapter;
    private final SalesReporter salesReporter;
    private final QuantityDrinkChecker quantityDrinkChecker;
    private final EmailNotifier emailNotifier;

    public CoffeeMachine(
            DrinkMaker drinkMaker,
            OrderInterface orderInterface,
            MoneyChecker moneyChecker,
            SalesReporter salesReporter,
            QuantityDrinkChecker quantityDrinkChecker,
            EmailNotifier emailNotifier
    ) {
        this.drinkMaker = drinkMaker;
        this.orderInterface = orderInterface;
        this.moneyChecker = moneyChecker;
        this.salesReporter = salesReporter;
        this.quantityDrinkChecker = quantityDrinkChecker;
        this.emailNotifier = emailNotifier;
        this.orderAdapter = new OrderAdapter();
        this.messageAdapter = new MessageAdapter();
    }

    public void takeOrder() {
        Order order = orderInterface.readInput();
        Money difference = moneyChecker.getDifference(order);
        if (difference.getAmount() < 0) {
            displayInsufficientFundsMessage(difference);
            return;
        }
        if(quantityDrinkChecker.isEmpty(order.getDrinkType().toString())) {
            emailNotifier.notifyMissingDrink(order.getDrinkType().toString());
            return;
        }
        salesReporter.addSale(order);
        String instructions = orderAdapter.adapt(order);
        drinkMaker.process(instructions);
    }

    private void displayInsufficientFundsMessage(Money difference) {
        NotEnoughMoneyMessage message = new NotEnoughMoneyMessage(difference);
        String instructions = messageAdapter.adapt(message);
        drinkMaker.process(instructions);
    }

    public void reportSales() {
        salesReporter.report();
    }
}
