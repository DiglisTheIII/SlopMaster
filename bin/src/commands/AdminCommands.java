package commands;

import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Mentions;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AdminCommands {

    JDA jda;

    List<Member> memberList;
    String guildID;
    MessageReceivedEvent event;

    public AdminCommands(MessageReceivedEvent event) {
        this.event = event;

        guildID = event.getGuild().getId();

        memberList = event.getGuild().getMembers();
    }

    public void kick(MessageReceivedEvent event) {
        List<Member> userToKick = event.getMessage().getMentions().getMembers();
        event.getGuild().kick(UserSnowflake.fromId(userToKick.get(0).getIdLong()));
        

    }
    
}
