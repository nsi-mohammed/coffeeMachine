package coffeemachine.objects.message;

import coffeemachine.objects.Money;
import coffeemachine.objects.alerts.NotEnoughMoneyMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotEnoughMoneyMessageTest {

    @Test
    public void generatesTheAppropriateTest() throws Exception {
        Money difference = new Money(-99);
        NotEnoughMoneyMessage message = new NotEnoughMoneyMessage(difference);
        assertEquals("There are 0,99 € missing", message.getText());
    }
}