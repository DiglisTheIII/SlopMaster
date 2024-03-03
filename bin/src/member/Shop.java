package member;

import java.lang.reflect.Field;
import java.util.List;
import java.awt.Color;

import bin.util.FileIO;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tools.CustomEmbed;

public class Shop extends ListenerAdapter {
    MessageReceivedEvent event;
    String[] message;
    int muntBucks;
    FileIO io;
    List<Role> roles;
    UserSnowflake sn;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        this.event = event;
        message = event.getMessage().getContentRaw().split(" ");
        io = new FileIO("bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + ".txt");
        muntBucks = Integer.parseInt(io.readFileLine(1));
        roles = event.getGuild().getRoles();
        sn = event.getMember();


        if (message[0].equals("m$role") && muntBucks >= 150000) {
            customRole();
            muntBucks -= 150000;
            exchange(muntBucks);
        }

        if(message[0].equals("m$shop")) {
            listItems();
        }
    }

    public void listItems() {
        final String[] items = {"Custom Role (150,000 mB)", "Change Server Name (75,000,000 mB)"};
        CustomEmbed emb = new CustomEmbed("----Shop----", "Buyable things in the MuntShop", "black", items);
        event.getChannel().sendMessageEmbeds(emb.build()).queue();
    }

    public void customRole() {

        try {
            Field field = Class.forName("java.awt.Color").getField(message[2]);
            Color color = (Color) field.get(null);

            event.getGuild().createRole().setName(message[1]).setColor(color).complete();
    
            for (Role r : roles) {
                if (r.getName().equals(message[1])) {
                    event.getGuild().addRoleToMember(sn, r).queue();
                }
            }
        } catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void exchange(int muntBucks) {
        io.modifyLineInFile(String.valueOf(muntBucks), 1);
    }

    public void changeName() {
        //event.getGuild().get
    }
}
