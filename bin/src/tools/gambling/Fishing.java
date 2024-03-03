package tools.gambling;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tools.CustomEmbed;
import util.SendMessage;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import bin.util.FileIO;
import member.MemberInfo;

public class Fishing extends ListenerAdapter {

    MessageReceivedEvent event;
    MemberInfo mem;
    FileIO io;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        this.event = event;
        if(event.getAuthor().isBot()) return;
        mem = new MemberInfo("bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + ".txt");
        String com = event.getMessage().getContentRaw().split(" ")[0];
        io = new FileIO("bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + ".txt");

        if(com.equals("m$fish")) {
            castLine();
            return;
        } 

        if(com.equals("m$inventory")) {
            getPlayerInventory();
            return;
        }

        if(com.equals("m$value")) {
            totalValue();
            return;
        }
        if(com.equals("m$sell")) {
            sell();
            return;
        }
    }
    
    public void castLine() {
        Message msg = event.getMessage();
        int rand = ThreadLocalRandom.current().nextInt(0, 30);
        if(rand > 4) {
            msg.delete().complete();
            SendMessage.sendMessage(event, "You suck at fishing.").queue();
        } else {
            event.getChannel().sendMessage(Items.getNameFromValue(rand)).queue();
            mem.updateInventory(rand);
        }
    }

    public void getPlayerInventory() {
        CustomEmbed emb;
        String[] items = new String[mem.getInventory().size()];
        for(int i = 0; i < mem.getInventory().size(); i++) {
            items[i] = mem.getInventory().get(i).getItemName().concat(" Value: " + mem.getInventory().get(i).getCost());
        }

        emb = new CustomEmbed("----Inventory----", "Your inventory", "black", items);
        event.getChannel().sendMessageEmbeds(emb.build()).queue();

    }

    public int totalValue() {
        int price = 0;
        for(int i = 0; i < mem.getInventory().size(); i++) {
            price += mem.getInventory().get(i).getCost();
        }

        SendMessage.sendMessage(event, "Total value of inventory: " + price + " Munt Bucks").queue();
        return price;
    }

    public void sell() {
        String name = io.readFileLine(0);
        String munts = io.readFileLine(1);
        String muntDebt = io.readFileLine(2);

        int totalMunts = Integer.valueOf(munts) + totalValue();

        munts = String.valueOf(totalMunts);

        io.deleteFile();
        io.createFile();
        String[] data = {name.concat("\n"), munts.concat("\n"), muntDebt.concat("\n\n\n\n\n")};
        io.writeToFile(data);
    }

    public List<Integer> lootTable() {
        List<Integer> rolls = new ArrayList<Integer>();
        int rand;
        for(int i = 0; i < 4; i++) {
            rand = ThreadLocalRandom.current().nextInt(0, 5);
            rolls.add(rand);
        }
        return rolls;
    }

}
