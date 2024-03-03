package commands;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import tools.BackupUserData;
import java.util.ArrayList;
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
        if (isAdmin) {
            List<Member> userToKick = event.getMessage().getMentions().getMembers();
            event.getGuild().kick(UserSnowflake.fromId(userToKick.get(0).getIdLong()));
            SendMessage.sendMessage(event, userToKick.get(0).getEffectiveName());
        } else {
            def.queue();
        }
    }

    public void createBackup() {
        if (isAdmin) {
            try {
                BackupUserData.backupFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            def.queue();
        }
    }

    public void shuffleNames() {
        if (isAdmin) {
            List<Member> members = event.getGuild().getMembers();
            ArrayList<String> names = new ArrayList<String>();
            for (int i = 0; i < members.size(); i++) {
                names.add(members.get(i).getEffectiveName());
            }
            Collections.shuffle(names);
            for (int i = 0; i < names.size(); i++) {
                if(!members.get(i).hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)) {
                    members.get(i).modifyNickname(names.get(i)).complete();
                }

            }
            
        }

    }

    public void getActivities() {
        
    }

}
