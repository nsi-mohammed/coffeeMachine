package coffeemachine;

import coffeemachine.checker.MoneyChecker;
import coffeemachine.objects.DrinkType;
import coffeemachine.objects.Money;
import coffeemachine.objects.Order;
import coffeemachine.objects.Menu;
import coffeemachine.moneychecker.AlwaysEnoughMoney;
import coffeemachine.checker.QuantityDrinkChecker;
import coffeemachine.interfaces.DrinkMaker;
import coffeemachine.interfaces.EmailNotifier;
import coffeemachine.interfaces.OrderInterface;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FunctionalTest {

    private Mockery context;
    private DrinkMaker drinkMaker;
    private OrderInterface orderInterface;
    private MoneyChecker moneyChecker;
    private SalesReporter salesReporter;
    private QuantityDrinkChecker quantityDrinkChecker;
    private EmailNotifier emailNotifier;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        drinkMaker = context.mock(DrinkMaker.class);
        orderInterface = context.mock(OrderInterface.class);
        moneyChecker = context.mock(MoneyChecker.class);
        salesReporter = context.mock(SalesReporter.class);
        quantityDrinkChecker = context.mock(QuantityDrinkChecker.class);
        emailNotifier = context.mock(EmailNotifier.class);
    }

    private CoffeeMachine freeMachine() {
        return new CoffeeMachine(
                drinkMaker,
                orderInterface,
                new AlwaysEnoughMoney(),
                salesReporter,
                quantityDrinkChecker,
                emailNotifier
        );
    }

    private CoffeeMachine moneyCheckMachine() {
        return new CoffeeMachine(
                drinkMaker,
                orderInterface,
                moneyChecker,
                salesReporter,
                quantityDrinkChecker,
                emailNotifier
        );
    }

    @Test
    public void teaWithOneSugarAndAStickOrder() throws Exception {
        final Order order = new Order(Menu.TEA);
        order.addSugar();

        context.checking(new Expectations() {{
            ignoring(quantityDrinkChecker);
            ignoring(emailNotifier);

            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(salesReporter).addSale(order);

            oneOf(drinkMaker).process("T:1:0");
        }});

        CoffeeMachine coffeeMachine = freeMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void hotChocolateWithNoSugarAndNoStickOrder() throws Exception {
        final Order order = new Order(Menu.HOT_CHOCOLATE);

        context.checking(new Expectations() {{
            ignoring(quantityDrinkChecker);
            ignoring(emailNotifier);

            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(salesReporter).addSale(order);

            oneOf(drinkMaker).process("H::");
        }});

        CoffeeMachine coffeeMachine = freeMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void coffeeWithTwoSugarsAndAStickOrder() throws Exception {
        final Order order = new Order(Menu.COFFEE);
        order.addSugar();
        order.addSugar();

        context.checking(new Expectations() {{
            ignoring(quantityDrinkChecker);
            ignoring(emailNotifier);

            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(salesReporter).addSale(order);

            oneOf(drinkMaker).process("C:2:0");
        }});

        CoffeeMachine coffeeMachine = freeMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void orangeJuice() throws Exception {
        final Order order = new Order(Menu.ORANGE_JUICE);

        context.checking(new Expectations() {{
            ignoring(quantityDrinkChecker);
            ignoring(emailNotifier);

            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(salesReporter).addSale(order);

            oneOf(drinkMaker).process("O::");
        }});

        CoffeeMachine coffeeMachine = freeMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void notEnoughMoneyInserted() throws Exception {
        final Order order = new Order(new Menu(DrinkType.TEA, null));

        context.checking(new Expectations() {{
            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(moneyChecker).getDifference(order);
            will(returnValue(new Money(-40)));

            never(salesReporter).addSale(order);

            oneOf(drinkMaker).process("M:There are 0,40 € missing");
        }});

        CoffeeMachine coffeeMachine = moneyCheckMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void forwardsOrderIfMoneyInsertedGreaterThanPrice() throws Exception {
        final Order order = new Order(new Menu(DrinkType.TEA, null));

        context.checking(new Expectations() {{
            ignoring(quantityDrinkChecker);
            ignoring(emailNotifier);

            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(moneyChecker).getDifference(order);
            will(returnValue(new Money(40)));

            oneOf(salesReporter).addSale(order);

            oneOf(drinkMaker).process("T::");
        }});

        CoffeeMachine coffeeMachine = moneyCheckMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void forwardsOrderIfMoneyInsertedEqualsThanPrice() throws Exception {
        final Order order = new Order(new Menu(DrinkType.TEA, null));

        context.checking(new Expectations() {{
            ignoring(quantityDrinkChecker);
            ignoring(emailNotifier);

            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(moneyChecker).getDifference(order);
            will(returnValue(new Money(0)));

            oneOf(salesReporter).addSale(order);

            oneOf(drinkMaker).process("T::");
        }});

        CoffeeMachine coffeeMachine = moneyCheckMachine();
        coffeeMachine.takeOrder();
    }

    @Test
    public void reportsSales() throws Exception {

        context.checking(new Expectations() {{
            never(orderInterface);
            never(moneyChecker);
            never(drinkMaker);

            oneOf(salesReporter).report();

        }});
        CoffeeMachine coffeeMachine = moneyCheckMachine();
        coffeeMachine.reportSales();

    }

    @Test
    public void notifiesShortages() throws Exception {

        final Order order = new Order(new Menu(DrinkType.TEA, null));

        context.checking(new Expectations() {{
            oneOf(orderInterface).readInput();
            will(returnValue(order));

            oneOf(moneyChecker).getDifference(order);
            will(returnValue(new Money(0)));

            oneOf(quantityDrinkChecker).isEmpty(DrinkType.TEA.toString());
            will(returnValue(true));

            oneOf(emailNotifier).notifyMissingDrink(DrinkType.TEA.toString());

            never(salesReporter);
            never(drinkMaker);
        }});

        CoffeeMachine coffeeMachine = moneyCheckMachine();
        coffeeMachine.takeOrder();
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }
}