import java.io.FileWriter;
import java.io.IOException;

import commands.AdminCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    public static void main(String[] args) {
        JDA jda = JDABuilder.createDefault("OTQ1MjUwNjA0ODQ1MDA2ODQ4.GDt1lf.6_T0XHQ3xBF99nUimw32APSvb3hMBoWkOMOWWI")
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
        .addEventListeners(new CommandHandler()).build();
    }

}

class CommandHandler extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        AdminCommands adm = new AdminCommands(event);
        String[] message = event.getMessage().getContentRaw().split(" ");
        String error = "";
        String command = message[0];
        try {
            String commandPref = message[0].substring(0, 2).equals("s$") ? "s$" : "";
            command = message[0].substring(2);

            if(commandPref.equals("s$") && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                switch(command) {
                    case "kick":
                        adm.kick(event);
                }
            }
        } catch(StringIndexOutOfBoundsException ex) {
            try {
                FileWriter writer = new FileWriter("./ErrorLog.txt");
                writer.write(command);
                writer.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }

}