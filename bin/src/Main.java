import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import commands.AdminCommands;
import commands.MemberCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import tools.LogEvent;
import tools.funny.LeagueListener;
import tools.gambling.Blackjack;
import util.SendMessage;
import util.Token;

public class Main {

    static JDA jda;
    public static void main(String[] args) throws IOException {
        jda = JDABuilder.createDefault(Token.token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES)
        .enableCache(CacheFlag.ACTIVITY)
        .addEventListeners(new CommandHandler(), new Blackjack(), new LeagueListener()).build();
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
                    case "help":
                        com.help(event).queue();
                        break;
                    case "activity":
                        adm.getActivities();
                        break;
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