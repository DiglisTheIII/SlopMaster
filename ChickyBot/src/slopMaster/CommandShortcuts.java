package slopMaster;

import java.io.File;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

public class CommandShortcuts extends ListenerAdapter {
	
	public void sendMessage(GuildMessageReceivedEvent event, String message, boolean isReply) {
		if(!isReply) {
			event.getChannel().sendMessage(message).queue();
		} else {
			event.getMessage().reply(message).queue();
		}
	}
	
	//For delays and other specific rest action events
		public RestAction<?> sendMessageQueue(GuildMessageReceivedEvent event, String message, boolean isReply) {
			if(isReply) {
				return event.getMessage().reply(message);
			} else {
				return event.getChannel().sendMessage(message);
			}

		}
	
	//Overloaded method for files
	public void sendMessage(GuildMessageReceivedEvent event, File f) { event.getChannel().sendFile(f).queue(); }
	
	//Overloaded method for embeds
	public void sendMessage(GuildMessageReceivedEvent event, MessageEmbed embed) { event.getChannel().sendMessage(embed).queue(); }
	
	//Overloaded for embed attachments
	public void sendMessage(GuildMessageReceivedEvent event, MessageEmbed embed, File f) { event.getChannel().sendMessage(embed).addFile(f).queue(); }
	
	public void deleteMessage(GuildMessageReceivedEvent event) { event.getMessage().delete().queue(); }
	
	public void react(GuildMessageReceivedEvent event, String emote) { event.getMessage().addReaction(emote).queue(); }
	
	public void sendDM(GuildMessageReceivedEvent event, String message) { event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue(); }
	
	//Overloaded method for specific user DM
	public void sendDM(GuildMessageReceivedEvent event, User user, String message) { user.openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue(); }
	
	public RestAction<?> sendEmbedQueue(GuildMessageReceivedEvent event, MessageEmbed embed) { return event.getChannel().sendMessage(embed); }
}
