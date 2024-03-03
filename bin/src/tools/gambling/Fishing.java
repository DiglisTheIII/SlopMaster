package tools.gambling;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tools.CustomEmbed;
import util.SendMessage;

import java.util.List;
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
    }
    
    public void castLine() {
        int rand = ThreadLocalRandom.current().nextInt(0, 30);
        if(rand > 4) {
            SendMessage.sendMessage(event, rand + "You caught nothing retard.").queue();
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

    public void totalValue() {
        int price = 0;
        for(int i = 0; i < mem.getInventory().size(); i++) {
            price += mem.getInventory().get(i).getCost();
        }

        SendMessage.sendMessage(event, "Total value of inventory: " + price + " Munt Bucks").queue();
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
