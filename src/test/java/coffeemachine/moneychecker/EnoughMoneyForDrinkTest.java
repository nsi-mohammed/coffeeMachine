package coffeemachine.moneychecker;

import coffeemachine.interfaces.MoneyInterface;
import coffeemachine.objects.DrinkType;
import coffeemachine.objects.Money;
import coffeemachine.objects.Order;
import coffeemachine.objects.Menu;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EnoughMoneyForDrinkTest
{

    @Parameters(name = "{index}: insertedCents={0}, drinkPriceCents={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {0, 99},
                {44, 99},
                {99, 99},
                {123, 99}
        });
    }

    private int insertedCents;
    private int drinkPriceCents;
    private Mockery context;
    private MoneyInterface moneyInterface;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        moneyInterface = context.mock(MoneyInterface.class);
    }

    public EnoughMoneyForDrinkTest(int insertedCents, int drinkPriceCents) {
        this.insertedCents = insertedCents;
        this.drinkPriceCents = drinkPriceCents;
    }

    @Test
    public void returnsMoneyDifference() throws Exception {
        Menu drink = new Menu(DrinkType.COFFEE, new Money(drinkPriceCents));
        final Order order = new Order(drink);
        final Money insertedMoney = new Money(insertedCents);

        context.checking(new Expectations() {{
            oneOf(moneyInterface).readMoney();
            will(returnValue(insertedMoney));
        }});

        EnoughMoneyForDrink checker = new EnoughMoneyForDrink(moneyInterface);
        assertEquals(new Money(insertedCents-drinkPriceCents), checker.getDifference(order));
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }
}
