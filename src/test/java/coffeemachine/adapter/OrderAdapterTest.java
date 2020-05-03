package coffeemachine.adapter;

import coffeemachine.objects.Order;
import coffeemachine.objects.Menu;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderAdapterTest {

    @Test
    public void adaptsTea() throws Exception {
        Order order = new Order(Menu.TEA);
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("T::", instructions);
    }

    @Test
    public void adaptsCoffee() throws Exception {
        Order order = new Order(Menu.COFFEE);
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("C::", instructions);
    }

    @Test
    public void adaptsHotChocolate() throws Exception {
        Order order = new Order(Menu.HOT_CHOCOLATE);
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("H::", instructions);
    }

    @Test
    public void adaptsHotChocolateWithOneSugar() throws Exception {
        Order order = new Order(Menu.HOT_CHOCOLATE);
        order.addSugar();
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("H:1:0", instructions);
    }

    @Test
    public void adaptsOrangeJuice() throws Exception {
        Order order = new Order(Menu.ORANGE_JUICE);
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("O::", instructions);
    }

    @Test
    public void adaptsExtraHotCoffeeWithNoSugar() throws Exception {
        Order order = new Order(Menu.COFFEE);
        order.setExtraHot();
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("Ch::", instructions);
    }

    @Test
    public void adaptsExtraHotChocolateWithOneSugar() throws Exception {
        Order order = new Order(Menu.HOT_CHOCOLATE);
        order.addSugar();
        order.setExtraHot();
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("Hh:1:0", instructions);
    }

    @Test
    public void adaptsExtraHotTeaWithTwoSugar() throws Exception {
        Order order = new Order(Menu.TEA);
        order.addSugar();
        order.addSugar();
        order.setExtraHot();
        OrderAdapter adapter = new OrderAdapter();
        String instructions = adapter.adapt(order);
        assertEquals("Th:2:0", instructions);
    }
}