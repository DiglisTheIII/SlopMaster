package commands;

import java.io.IOException;
import java.security.Permission;
import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Mentions;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import tools.BackupUserData;
import util.SendMessage;

public class AdminCommands {

    JDA jda;

    List<Member> memberList;
    String guildID;
    MessageReceivedEvent event;
    RestAction<?> def;

    boolean isAdmin;

    public AdminCommands(MessageReceivedEvent event) {
        this.event = event;

        guildID = event.getGuild().getId();

        memberList = event.getGuild().getMembers();
        isAdmin = event.getMember().hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR);
        def = SendMessage.sendMessage(event, "Insufficient Permissions");
    }

    public void kick(MessageReceivedEvent event) {
        if(isAdmin) {
            List<Member> userToKick = event.getMessage().getMentions().getMembers();
            event.getGuild().kick(UserSnowflake.fromId(userToKick.get(0).getIdLong()));
            SendMessage.sendMessage(event, userToKick.get(0).getEffectiveName());
        } else {
            def.queue();
        }
    }

    public void createBackup() {
        if(isAdmin) {
            try {
                BackupUserData.backupFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            def.queue();
        }
    }
    
}
