import java.io.FileWriter;
import java.io.IOException;
import commands.AdminCommands;
import commands.MemberCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import tools.gambling.Blackjack;
import tools.gambling.Fishing;
import util.SendMessage;
import util.Token;

public class Main {

    static JDA jda;
    public static void main(String[] args) throws IOException {
        jda = JDABuilder.createDefault(Token.token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES)
        .enableCache(CacheFlag.ACTIVITY)
        .addEventListeners(new CommandHandler(), new Blackjack(), new Fishing()).build();
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
            String commandPref = message[0].substring(0, 2).equals("m$") ? "m$" : "";
            command = message[0].substring(2);
            if(commandPref.equals("m$")) {
                switch(command) {
                    case "reg":
                        com.createNewFile();
                        break;
                    case "muntloan":
                        com.getMuntLoan();
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
                    case "bankrupt":
                        com.bankruptcy();
                        break;
                    case "shufname":
                        adm.shuffleNames();
                        break;
                    case "acorn":
                        try {
                            com.tagAcorn();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    default: 
                        SendMessage.sendMessage(event, "Invalid command. Do m$help for a list of commands");
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