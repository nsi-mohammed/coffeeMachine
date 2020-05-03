package coffeemachine;

import coffeemachine.objects.Order;

public interface SalesReporter
{
    void addSale(Order order);

    void report();
}
