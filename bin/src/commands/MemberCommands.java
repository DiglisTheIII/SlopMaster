package commands;

import java.io.File;
import bin.util.FileIO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import tools.CustomEmbed;
import util.SendMessage;

public class MemberCommands {
    MessageReceivedEvent event;
    File guildFile;
    File user;
    FileIO io;

    public MemberCommands(MessageReceivedEvent event) {
        //Boilerplate variable initialization
        this.event = event;
        guildFile = new File("bin\\member\\" + event.getGuild().getName() + "\\");
        if(!guildFile.exists()) {
            guildFile.mkdirs();
        }

        user = new File(guildFile.getAbsolutePath() + "\\" + event.getMember().getEffectiveName() + ".txt");
        io = new FileIO(user);
    }

    //Creates user folder and file.
    public void createNewFile() {
        io.createFile();

        String[] data = {event.getMember().getEffectiveName().concat("\n"), "0\n", "0\n\n\n\n\n"};
        io.writeToFile(data);
        SendMessage.sendMessage(event, "You have been munted.").queue();
    }

    //Sets user data (Munt Bucks and Munt Loans) to specified amount.
    public void getMuntLoan() {
        String userMunts = event.getMessage().getContentRaw().split(" ")[1];
        
        if(Integer.valueOf(userMunts) > 5000) {
            SendMessage.sendMessage(event, "You cannot pull more than 5000 Munt Bucks").queue();
            return;
        }

        if(Integer.valueOf(userMunts) instanceof Integer) {
            io.modifyLineInFile(userMunts, 1);
            io.modifyLineInFile(userMunts, 2);
        }
    }

    public void bankruptcy() {
        io.modifyLineInFile("0", 1);
        io.modifyLineInFile("0", 1);
    }

    public void tagAcorn() throws InterruptedException {
        for(int i = 0; i < 20; i++) {
            Thread.sleep(500);
            SendMessage.sendMessage(event, "<@1075551511238680637> vc").queue();
        }
    }

    public void getData() {
        CustomEmbed data = new CustomEmbed("Munt Gambling", "All of your Munting needs right here.", "black", 
        "Munt Bucks: " + io.readFileLine(1),
        "Munt Debt: " + io.readFileLine(2));

        event.getChannel().sendMessageEmbeds(data.build()).queue();
    }

    
    public void payLoan() {
        int payment = Integer.valueOf(event.getMessage().getContentRaw().split(" ")[1]);
        int userMunts = Integer.valueOf(io.readFileLine(1));
        int userDebt = Integer.valueOf(io.readFileLine(2));
        if(userDebt == 0) {
            event.getChannel().sendMessage("You don't owe any Munts").queue();
            return;
        }

        if(payment > userMunts) {
            event.getChannel().sendMessage("You can't generate free Munt Bucks now, can you?").queue();
        } else if(payment <= userMunts) {
            userMunts = userMunts - payment;
            userDebt = userDebt - payment;

            io.modifyLineInFile(String.valueOf(userMunts), 1);
            io.modifyLineInFile(String.valueOf(userDebt), 2);
            getData();
        }
    }

    public RestAction<?> help(MessageReceivedEvent event) {
        final String title = "Command List";
        final String desc = "List of all commands";

        CustomEmbed help = new CustomEmbed(title, desc, "black", 
            "m$help - Displays list of commands.",
                      "m$reg - Registers you for gambling and loans.",
                      "m$muntloan [number amount] - Pulls loan up to signed 32-bit integer limit.",
                      "m$payloan - Pays off your loan with whatever slops you currently have.",
                      "m$muntjack [bet amount as number] - use m$hit and m$stand as needed, a standard game of Muntjack");
        
        return event.getChannel().sendMessageEmbeds(help.build());
    }

}
