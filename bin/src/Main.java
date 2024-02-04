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
import tools.gambling.Blackjack;
import util.SendMessage;
import util.ThreadHandler;
import util.Token;

public class Main {

    static JDA jda;
    public static void main(String[] args) throws IOException {
        jda = JDABuilder.createDefault(Token.token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
        .addEventListeners(new CommandHandler(), new Blackjack()).build();

        new LogEvent(jda.getHttpClient().toString() + " initialized to " + jda.getToken());
    }

    public static void addListener(ListenerAdapter adapter) {
        jda.addEventListener(adapter);
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
            ThreadHandler han = new ThreadHandler();
            if(commandPref.equals("s$")) {
                switch(command) {
                    case "reg":
                        com.createNewFile();
                        break;
                    case "sloploan":
                        com.getSlopLoan();
                        break;
                    case "payloan":
                        com.payLoan();
                        break;
                    case "backup":
                        adm.createBackup();
                        break;
                    case "info": 
                        com.getData();
                        break;
                    case "blackjack":
                        Blackjack blackjack = new Blackjack();

                    default: 
                        SendMessage.sendMessage(event, "Invalid command. Do s$help for a list of commands");
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