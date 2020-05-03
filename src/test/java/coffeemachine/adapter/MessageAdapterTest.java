package coffeemachine.adapter;

import coffeemachine.objects.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageAdapterTest {

    @Test
    public void adaptsMessage() throws Exception {
        Mockery context = new Mockery();
        final Message message = context.mock(Message.class);

        context.checking(new Expectations() {{
            oneOf(message).getText();
            will(returnValue("CoffeMachine"));
        }});

        MessageAdapter adapter = new MessageAdapter();
        String instructions = adapter.adapt(message);
        assertEquals("M:CoffeMachine", instructions);
    }
}