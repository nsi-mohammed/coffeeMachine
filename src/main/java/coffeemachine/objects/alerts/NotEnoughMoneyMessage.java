package coffeemachine.objects.alerts;

import coffeemachine.objects.Money;
import coffeemachine.objects.Message;

public class NotEnoughMoneyMessage implements Message
{
    private Money difference;

    public NotEnoughMoneyMessage(Money difference) {
        this.difference = difference;
    }

    public String getText() {
        return "There are " + difference.getAmount() + " missing";
    }
}
