package util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;

public class SendMessage {

    public static RestAction<?> sendMessage(MessageReceivedEvent event, String message) {
        return event.getChannel().sendMessage(message);
    }
    
}
