package tools.funny;

import java.util.List;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeagueListener extends ListenerAdapter {
    
    @Override
    public void onGenericUserPresence(GenericUserPresenceEvent event) {
        Member c = event.getMember();
        
        List<Activity> act = c.getActivities();

        if(act.size() == 0) {
            return;
        }

        String presence = act.get(0).toString().substring(13, act.get(0).toString().indexOf("("));

        TextChannel tc = (TextChannel) event.getGuild().getGuildChannelById("1202102110796402760");

        String activity;
        activity = presence.replaceAll(" ", "");
        activity = activity.toLowerCase();

        if(activity.equals("leagueoflegends")) {
            tc.sendMessage(event.getMember().getAsMention() + " is on " + presence + " make fun of them lol!!!!").queue();
        }

        System.out.println(presence);
    }

}
