import java.io.FileWriter;
import java.io.IOException;

import commands.AdminCommands;
import commands.MemberCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import tools.LogEvent;

public class Main {

    public static void main(String[] args) throws IOException {
        JDA jda = JDABuilder.createDefault("OTQ1MjUwNjA0ODQ1MDA2ODQ4.G0ktLB.ANXwH2dCSD7eFU7ZtOcRIGipCVcI8a6cL4Eq0k")
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
        .addEventListeners(new CommandHandler()).build();

        new LogEvent(jda.getHttpClient().toString() + " initialized to " + jda.getToken());
    }

}

class CommandHandler extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        AdminCommands adm = new AdminCommands(event);
        MemberCommands com = new MemberCommands(event);
        String[] message = event.getMessage().getContentRaw().split(" ");
        String command = message[0];
        try {
            String commandPref = message[0].substring(0, 2).equals("s$") ? "s$" : "";
            command = message[0].substring(2);

            if(commandPref.equals("s$")) {
                switch(command) {
                    case "reg":
                        com.createNewFile();
                        break;
                    case "sloploan":
                        com.getSlopLoan(false);
                        break;
                    case "payloan":
                        com.getSlopLoan(true);
                    case "backup":
                        adm.createBackup();
                        break;
                    case "info": 
                        com.getData();
                        break;
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