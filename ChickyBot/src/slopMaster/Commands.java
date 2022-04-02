package slopMaster;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Activity.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	public String message = "";
	public String prefix = "s$";
	public int joeCount = 0;
	public int maximumJoe = 100;
	public File f = new File("log.txt");
	public int randCounter = ThreadLocalRandom.current().nextInt(50, 250);
	public int sloppy = 0;
	public int yed = 0;
	public GuildMessageReceivedEvent event;
	public String[] pathArray = {"C:/Users/mmmmm/Desktop/chicken/1.png", 
			"C:/Users/mmmmm/Desktop/chicken/2.png",
			"C:/Users/mmmmm/Desktop/chicken/3.png",
			"C:/Users/mmmmm/Desktop/chicken/4.png",
			"C:/Users/mmmmm/Desktop/chicken/5.png",
			"C:/Users/mmmmm/Desktop/chicken/6.png",
			"C:/Users/mmmmm/Desktop/chicken/7.png",
			"C:/Users/mmmmm/Desktop/chicken/8.png",
			"C:/Users/mmmmm/Desktop/chicken/9.png",
			"C:/Users/mmmmm/Desktop/chicken/10.png",
			"C:/Users/mmmmm/Desktop/chicken/11.png",
			"C:/Users/mmmmm/Desktop/chicken/12.png",
			"C:/Users/mmmmm/Desktop/chicken/13.png",
			"C:/Users/mmmmm/Desktop/chicken/14.png",
			"C:/Users/mmmmm/Desktop/chicken/15.png"};
	
	public String[] gifPathArray = {"C:/Users/mmmmm/Desktop/chicken/acorn.gif",
			"C:/Users/mmmmm/Desktop/chicken/dance.gif"};
	
	public String nameChange = "";
	
	
	public JoeFileCount joeFile = new JoeFileCount();
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		//List<Role> allRoles = event.getGuild().getRoles();
		//allRoles = allRoles.subList(0, allRoles.size() - 1);
		
		CommandShortcuts scts = new CommandShortcuts();
		String[] args = event.getMessage().getContentRaw().split(" ");
		String userStr = event.getAuthor().toString().substring(2).replaceAll("[0-9()]", "");
		@SuppressWarnings("unused")
		boolean isBot = event.getAuthor().isBot();
		final boolean isServer = event.getGuild().getName().equals("Femboy Sorority");
		if(args[0].equalsIgnoreCase(prefix + "helpme")) {
			scts.sendDM(event, "Command List:\n"
					+ "s$hi - Slop Master replies with Greetings \n"
					+ "s$die - Slop Master replies with Love you too {@userID}\n"
					+ "s$lean - Slop Master will send a video with an Asuka/Venom Lean Meme.\n"
					+ "computer show me hell - Will send a gif of a skeleton burning in hell, be sure to thank computer afterwards!\n"
					+ "s$activity - Listens to the senders screams as an activity.\n"
					+ "s$cock - Sends a gif with a chicken.\n"
					+ "s$chickengif - Sends a guy who is very excited to eat some chicken!\n"
					+ "s$multichicken - Sends between 1-5 random pictures of fried chicken.\n"
					+ "s$soy {@userID} - Pings the user with a soy bounce gif.\n"
					+ "s$goodnight - A personal favorite :)\n"
					+ "s$warn {@userID} - Sends a dm to the pinged user, warning them.\n"
					+ "s$tuesday - Sends Out of Touch - Hall & Oates music video link\n"
					+ "computer kill joe biden - Probably the most complex command, this one sends an image to your server, counts up by one, and checks if the number has reached a maximum.\n"
					+ "Along with a bunch of stupid file shit.\n"
					+ "s$reset - This increases the maximum by a random amount between 50-249.\n"
					+ "s$play - Sets Slop Master's activity to playing a custom game of the senders choosing.\n"
					+ "s$listen - Sets Slop Master's activity to listening to a custom song.\n"
					+ "s$lonely - Sets Slop Master's status to Do Not Disturb");
		}
		
		if(args[0].equalsIgnoreCase(prefix + "silence")) {
			List<Member> mentionedMember = event.getMessage().getMentionedMembers();
			if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				event.getGuild().addRoleToMember(mentionedMember.get(0), event.getGuild().getRoleById("932112631546916884")).queue();
				scts.sendMessage(event, mentionedMember.get(0).getAsMention() + " has been silenced", false);
			} else if(event.getMember().isOwner() || !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				scts.sendMessage(event, "you cant do that", false);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "hi")) {
			scts.sendMessage(event, "Greetings, " + event.getAuthor().getAsMention(), true);
		}
		if(args[0].equalsIgnoreCase(prefix + "grease")) {
			User user = event.getAuthor();
			for(int i = 0; i < 5; i++) {	
				if(user.getId().equals("285179041777188875")) {
					scts.sendMessage(event,  "Don't play yourself", true);
					i = 5;
					break;
				}
				scts.deleteMessage(event);
				scts.sendMessage(event, "<@285179041777188875> fuck you", true);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "suckmyballs")) {
			List<Member> memberIDs = event.getGuild().getMembers();
			int randID = ThreadLocalRandom.current().nextInt(0, memberIDs.size());
			event.getChannel().sendTyping().queue();
			scts.sendMessage(event, "Suck my balls <@" + memberIDs.get(randID).getId() + ">", false);
		}
		if(args[0].equalsIgnoreCase(prefix + "lean")) {
			scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/carnage.mp4"));
		}
		
		if(args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("show") && args[2].equalsIgnoreCase("me") && args[3].equalsIgnoreCase("hell")) {
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setTitle(" ");
			embed.setImage("https://cdn.discordapp.com/attachments/956332484705013820/956628941211316254/hell.gif");
			scts.sendMessage(event, embed.build());
			
		}
		if(args[0].equalsIgnoreCase(prefix + "activity")) {
			scts.sendMessage(event, "done", true);
			event.getJDA().getPresence().setActivity(Activity.listening(event.getAuthor().getAsTag() + " screaming"));
		}
		if(args[0].equalsIgnoreCase(prefix + "activityclear")) {
			scts.sendMessage(event, "cleared", true);
			event.getJDA().getPresence().setActivity(Activity.playing("Los Pollos Hermanos"));
			
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println("ChickenBot activity has been reset.");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "cock")) {
			File f = new File("C:/Users/mmmmm/Desktop/chicken/cock.gif");
			
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Look at this cock!");
			embed.setImage("attachment://cock.gif");
			scts.sendMessage(event, embed.build(), f);
			
		}
		if(args[0].equalsIgnoreCase(prefix + "chicken")) {
			int rand = ThreadLocalRandom.current().nextInt(0, 15);
			File file = new File(pathArray[rand - 1]);
			
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("A chicken?!");
			embed.setImage("attachment://chicken.png");
			scts.sendMessage(event, embed.build(), file);
		}
		if(args[0].equalsIgnoreCase(prefix + "chickengif")) {
			int rand = ThreadLocalRandom.current().nextInt(0, 2);
			event.getChannel().sendFile(new File(gifPathArray[rand])).queue();
		}
		if(args[0].equalsIgnoreCase(prefix + "multichicken")) {
			int rand = ThreadLocalRandom.current().nextInt(0, 6);

			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("You asked for it...");
			embed.setImage("attachment://chicken.png");
			for(int i = 0; i < rand; i++) {
				int randFile = ThreadLocalRandom.current().nextInt(0, 16);
				File file = new File(pathArray[randFile - 1]);
				event.getChannel().sendMessage(embed.build()).addFile(file, "chicken.png").queue();
			}
		}
		
		if(args[0].equalsIgnoreCase(prefix + "specialModCommand")) {
			if(event.getAuthor().getId().equals("695688150466428989")) {
				for(int i = 0; i < 5; i++) {
					scts.deleteMessage(event);
					scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/casket-coffin.gif"));
				}
			} else {
				scts.deleteMessage(event);
				scts.sendMessage(event, "How did you get here?", true);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "fuckyougeo")) {
			for(int i = 0; i < 25; i++) {
				scts.sendMessage(event, "Fuck you die <@256920677385371649>", false);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "soy")) {
			IMentionable mentionedUser = event.getMessage().getMentions(MentionType.USER).get(0);
			String replace = mentionedUser.toString();
			long id = Long.parseLong(replace.replaceAll("[^0-9]", ""));
			scts.sendMessage(event, "<@" + String.valueOf(id) + ">", false);
			scts.sendMessage(event, "https://cdn.discordapp.com/attachments/931616914227208203/956269644455493632/bounce.gif", false);
			scts.deleteMessage(event);
		}
		if(args[0].equalsIgnoreCase(prefix + "goodnight")) {
			scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/sleep.mp4"));
		}
		if(args[0].equalsIgnoreCase(prefix + "unsilence")) {
			Member member = event.getMessage().getMentionedMembers().get(0);
			User author = event.getMessage().getAuthor();
			boolean isAdmin = true;
			if(!event.getGuild().getMember(author).hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)) {
				scts.sendMessage(event, "You are not an administrator idiot!", false);
				isAdmin = false;
			} 
			if(isAdmin) {
				event.getGuild().removeRoleFromMember(member, event.getGuild().getRoleById("932112631546916884")).queue();
				scts.sendMessage(event, member.getAsMention() + " Has been unmuted :(", false);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "kys")) {
			scts.deleteMessage(event);
			scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/kys.png"));
		}
		if(args[0].equalsIgnoreCase(prefix + "warn")) {
			for(int i = 0; i < args.length; i++) {
				message += args[i] += " ";
			}
			
			User user = event.getMessage().getMentionedUsers().get(0);
			scts.sendDM(event, user, message);
			scts.deleteMessage(event);
			
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println(event.getMessage().getMentionedMembers().toString() + " has been warned.");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("jango")) {
				i = args.length;
				final String emoteId = "felixsupremacy:944033771836022844";
				scts.react(event, emoteId);
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("benadryl")) {
				i = args.length;
				final String emoteId = "BENADRYL:943635931456495696";
				scts.react(event, emoteId);
			}
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("tuesday")) {
				i = args.length;
				String link = "Tuesday? Am I so out of touch? https://www.youtube.com/watch?v=D00M2KZH1J0";
				scts.sendMessage(event, link, false);
			}
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[0].equalsIgnoreCase("league") && args[1].equalsIgnoreCase("of") && args[2].equalsIgnoreCase("legends")) {
				i = args.length;
				scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/outofthegenepool.png"));
			}
		}
		if(args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("kill") && args[2].equalsIgnoreCase("joe") && args[3].equalsIgnoreCase("biden")) {
			String counter = String.valueOf(joeCount);
			joeCount++;
			try {
				joeFile.writeFile(joeCount);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				counter = joeFile.readFile(joeFile.joe);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(joeCount <= 10 && joeCount > 1) {
				event.getChannel().sendTyping().delay(Duration.ofSeconds(5)).queue();
				scts.sendMessage(event, "I have killed Joe Biden, here is the video.", false);
				scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/biden.gif"));
				event.getChannel().sendMessage("Joe Biden has been killed " + counter + " times.").queue();
			} else if (joeCount == 1) {
				event.getChannel().sendTyping().delay(Duration.ofSeconds(5)).queue();
				scts.sendMessage(event, "I have killed Joe Biden, here is the video.", false);
				scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/biden.gif"));
				event.getChannel().sendMessage("Joe Biden has been killed " + counter + " times.").queue();
			}
			
			else if(joeCheck(joeCount) == maximumJoe) {
				scts.sendMessage(event, "Oops, Joe Biden is now perma dead!", false);
			}
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println("Joe Biden death count raised to " + joeCount);
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "joekillcount")) {
			String counter = String.valueOf(joeCount);
			try {
				counter = joeFile.readFile(joeFile.joe);
			} catch (IOException e) {
				e.printStackTrace();
			}
			scts.sendMessage(event, counter, false);
		}
		
		if(args[0].equalsIgnoreCase(prefix + "reset")) {
			
			int totalJoe = joeCount;
			if(joeCount == maximumJoe) {
				if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					scts.sendMessage(event, "They respawned!!!!", false);
					joeCount = 0;
				}
			} else if(joeCount < maximumJoe) {
				totalJoe += randCounter;
				scts.sendMessage(event, "\u26A0 Here they come...", false);
			}
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println("Total Joe Biden count increased to " + totalJoe);
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		if(args[0].equalsIgnoreCase(prefix + "play")) {
			String activity = "";
			for(int i = 1; i < args.length; i++) {
				activity += args[i] += " ";
				event.getJDA().getPresence().setActivity(Activity.playing(activity));
			}
			scts.sendMessage(event, "I am now playing " + activity, false);	
			
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println("ChickenBot activity changed to: playing " + event.getJDA().getPresence().getActivity().toString());
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(args[0].equalsIgnoreCase(prefix + "listen")) {
			String activity = "";
			for(int i = 1; i < args.length; i++) {
				activity += args[i] += " ";
				event.getJDA().getPresence().setActivity(Activity.listening(activity));
			}
			scts.sendMessage(event, "I am now listening to " + activity, false);	
			
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println("ChickenBot activity changed to: listening to " + event.getJDA().getPresence().getActivity().toString());
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(args[0].equalsIgnoreCase("!rule34")) {
			scts.sendMessage(event, "Horny", true);
		}
		if(args[0].equalsIgnoreCase(prefix + "nuke")) {
			if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				scts.deleteMessage(event);
				scts.sendMessage(event, "This channel will be cleared in 10 seconds", false);
				event.getChannel().createCopy().queue();
	            event.getChannel().delete().queueAfter(10, TimeUnit.SECONDS);
	    
			} else if(!event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				scts.sendMessage(event, "You are not an admin, get fucked", true);
			}
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println(event.getChannel().getName() + " has been cleared");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "offyourself")) {
			if(event.getAuthor().getId().equals("695688150466428989")) {
				scts.sendMessage(event, "Ok :( goodbye", true);
				event.getJDA().shutdownNow();
				try {
					PrintWriter pw = new PrintWriter(new FileWriter(f, true));
					pw.println("Slop Master shut down for maintenance");
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				scts.sendMessage(event, "You cannot initiate shutdown sequence.", true);
			}
			
			
		}
		
		if(args[0].equalsIgnoreCase(prefix + "lonely")) {
			event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
			scts.sendMessage(event, "Do not speak to me...", true);
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("wales")) {
				i = args.length;
				scts.sendMessage(event, "Llanfairpwllgwyngyllgogerychrywndrobwllllantysiliogogogoch", false);
			}
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("trump")) {
				i = args.length;
				scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/trump.png"));
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("jp")) {
				i = args.length;
				scts.react(event, "trand:946483227231649823");
			}
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("destiny")) { 
				i = args.length;
				scts.sendMessage(event, new File("C:/Users/mmmmm/desktop/botgifs/out.gif"));			
			} else if(args[i].equalsIgnoreCase("hate") && args[i + 1].equalsIgnoreCase("destiny")) {
				i = args.length;
				scts.sendMessage(event, new File("C:/Users/mmmmm/desktop/botgifs/basedchad.gif"));
			}
		}
		
		if(args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("show") && args[2].equalsIgnoreCase("me") && args[3].equalsIgnoreCase("shit")) {
			event.getChannel().sendTyping().queue();
			scts.sendMessage(event, new File("C:/Users/mmmmm/desktop/botgifs/shitgame.png"));
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("walt")) {
				scts.sendMessage(event, new File("C:/Users/mmmmm/desktop/botgifs/walt.png"));
			}
		}
		
		if(event.getChannel().isNSFW() && args[0].equalsIgnoreCase("horny") && !event.getAuthor().isBot()) {
			scts.sendMessage(event, "https://cdn.discordapp.com/attachments/932062321835118613/950808082223607859/nfw.gif", true);
		} else if((!event.getChannel().isNSFW()) && args[0].equalsIgnoreCase("horny")) {
			scts.sendMessage(event, "You cannot be horny here", false);
		}
		try {
			if(args[0].equalsIgnoreCase("slop") && args[1].equalsIgnoreCase("me")) {
				sloppy++;
				if(sloppy == 1) {
					scts.sendMessage(event, "You have been slopped " + sloppy + " time.", true);
				} else if(sloppy > 1) {
					scts.sendMessage(event, "You have been slopped " + sloppy + " times.", true);
				}
				try {
					PrintWriter pw = new PrintWriter(new FileWriter(f, true));
					pw.println(event.getAuthor().getAsTag() + " has been slopped");
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(IndexOutOfBoundsException e) {
			if(args[0].equalsIgnoreCase("slop")) {
				IMentionable mentionedUser =  event.getMessage().getMentions(MentionType.USER).get(0);
				String replace = mentionedUser.toString();
				long id = Long.parseLong(replace.replaceAll("[^0-9]", ""));
				scts.sendMessage(event, "<@" + String.valueOf(id) + "> has been slopped.", false);
			}
		}
		if(args[0].equalsIgnoreCase("unslop") && args[1].equalsIgnoreCase("me")) {
			if(sloppy == 0) {
				scts.sendMessage(event, "there is no slop to unslop!", false);
			} else {
				scts.sendMessage(event, "no more slop!", false);
				sloppy = 0;
			}
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f, true));
				pw.println("slop reset");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "incarcerate")) {
			scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/prison.jpg"));
			event.getJDA().shutdown();
		}
	
		for(int i = 0; i < args.length; i++) {
			boolean isAdmin = false;
			if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				isAdmin = true;
			}
			
			if(args[i].equalsIgnoreCase("genshin") && !isAdmin) {
				event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("932112631546916884")).queue();
				scts.sendMessage(event, "Muted lol", true);
			}
			else if(args[i].equalsIgnoreCase("genshin") && isAdmin) {
				scts.sendMessage(event, "i cant mute you :(", true);
			}
		}
		
		//Puts every role in the server, minus @everyone in the list
		List<Role> allRoles = event.getGuild().getRoles();
		allRoles = allRoles.subList(0, allRoles.size() - 1);
		if(args[0].equalsIgnoreCase(prefix + "role")) {
			try {
				//Debug stuff
				String role = "";
				boolean nullRole = false;
				for(int i = 0; i < allRoles.size(); i++) {
					System.out.println(allRoles.get(i));
				}
				if(args.length > 1) {
					for(int i = 1; i < args.length; i++) {
						//This takes the message, drops the command prefix, and just gets the role they want
						role += args[i] + " ";
						//patman was here
					}
					//System.out.println(role);
				} else if(args.length == 2) {
					//If it is just a one word role, this sets the role variable to that argument
					role = args[1];
				}
				for(int i = 8; i < allRoles.size(); i++) { //i = 8 specifically for my server REMINDER: go back and make it dynamic per server
					//checks if role is equal to the raw string name of a sublist of allRoles
					if(role.contains(allRoles.get(i).toString().substring(2).replaceAll("[0-9()]", ""))  && (!args[1].equalsIgnoreCase("Sinful") && !args[2].equalsIgnoreCase("Fool"))) {
						//Gets rid of everything in the Role but the ID, which is a long value as string
						String rolee = allRoles.get(i).toString().replaceAll("[a-zA-Z():]", "").substring(1).trim();
						//System.out.println(rolee);
						event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rolee)).queue();
						scts.sendMessage(event, "roled", true);
						nullRole = true;
						try {
							PrintWriter pw = new PrintWriter(new FileWriter(f, true));
							pw.println(userStr + " was given role " + role);
							pw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} 
				}
				if(!nullRole || (args[1].equalsIgnoreCase("Sinful") && args[2].equalsIgnoreCase("Fool"))) {
					scts.sendMessage(event, "this shit ain't fr!", true);
				}
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "unrole")) {
			try {
				String role = "";
				boolean nullRole = false;
				for(int i = 0; i < allRoles.size(); i++) {
					System.out.println(allRoles.get(i));
				}
				if(args.length > 1) {
					for(int i = 1; i < args.length; i++) {
						role += args[i] + " ";
					}
				} else if(args.length == 2) {
					role = args[1];
				}
				for(int i = 8; i < allRoles.size(); i++) {
					if(role.contains(allRoles.get(i).toString().substring(2).replaceAll("[0-9()]", "")) && (!args[1].equalsIgnoreCase("Sinful") && !args[2].equalsIgnoreCase("Fool"))) {
						String rolee = allRoles.get(i).toString().replaceAll("[a-zA-Z():]", "").substring(1).trim();
						//System.out.println(rolee);
						event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rolee)).queue();
						scts.sendMessage(event, "role taken away. bye bye", true);
						nullRole = true;
						try {
							PrintWriter pw = new PrintWriter(new FileWriter(f, true));
							pw.println(userStr + " was removed from role:  " + role);
							pw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} 
				}
				if(!nullRole && (args[1].equalsIgnoreCase("Sinful") && args[2].equalsIgnoreCase("Fool"))) {
					scts.sendMessage(event, "this shit ain't fr!", true);
				}
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "rolelist")) {
			String[] rolesArr = new String[allRoles.size()]; //setting string array length as allRoles
			String everyRole = "";
			for(int i = 0; i < allRoles.size(); i++) {
				rolesArr[i] = allRoles.get(i).toString().substring(2).replaceAll("[0-9()]", ""); //replacing all the useless mumbo jumbo in the roles so its easier to read
				everyRole += rolesArr[i] += "\n";
			}
			final String privateRollList = everyRole; //this only exists since only final variables can be sent in dms
			scts.sendMessage(event, "snent", true);
			scts.sendDM(event, privateRollList);
		}
		if(args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("show") && args[2].equalsIgnoreCase("me") && args[3].equalsIgnoreCase("heaven")) {
			scts.sendMessage(event, new File("C:/Users/mmmmm/Desktop/botgifs/heaven.png"));
		}
		int count = event.getGuild().getMemberCount();
		if(args[0].equalsIgnoreCase(prefix + "usercount")) {
			scts.sendMessage(event, "There are " + count + " people in the server", false);
		}
		if(args[0].equalsIgnoreCase(prefix + "banme")) {
			int troll = count - 1;
			scts.deleteMessage(event);
			scts.sendMessage(event, event.getAuthor().getAsMention() + " has been banned *(" + troll + ")*", true);
		}
		if(args[0].equalsIgnoreCase(prefix + "ban")) {
			List<Member> mentions = event.getMessage().getMentionedMembers(); //gets mentioned members from the command
			if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				event.getGuild().ban(mentions.get(0), Integer.parseInt(args[2])).queue(); //setting time argument as an int to for ban timing
				scts.sendMessage(event, mentions.get(0).getAsMention() + " has been banned", false);
			} else if(!event.getMember().isOwner() || !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				scts.sendMessage(event, "You cannot do that", true);
			}
		}
		
		if(args[0].equalsIgnoreCase("gay") && args[1].equalsIgnoreCase("amongus")) {
			scts.sendMessage(event, "ð��˜", false);
		}
		if((args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("am") && args[2].equalsIgnoreCase("i") && args[3].equalsIgnoreCase("cool"))) {
			scts.sendMessage(event, "yes", true);
			/*
			} else if(!event.getMember().isOwner() || !event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals("530269428185825290")) {
				final String emoteID = "quaint:949743841337024532";
				event.getMessage().reply("No").queue();
				event.getMessage().addReaction(emoteID).queue();
			}
			*/
		}
		
		if(args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("do") && args[2].equalsIgnoreCase("i") && args[3].equalsIgnoreCase("suck") && args[4].equalsIgnoreCase("dick")) {
			if(event.getAuthor().getId().equals("530269428185825290")) {
				scts.sendMessage(event, "YES!", false);
			} else {
				scts.sendMessage(event, "NO!", false);
			}
		}
		if(args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("do") && args[2].equalsIgnoreCase("i") && args[3].equalsIgnoreCase("deserve") && args[4].equalsIgnoreCase("admin?")) {
			if(event.getMember().getId().equals("695688150466428989") || event.getMember().getId().equals("283409606779338762")) {
				scts.sendMessage(event, "yes", false);
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("sand") && !event.getAuthor().isBot()) {
				scts.sendMessage(event, "i eated all the sand", false);
			}
		}
		List<Member> membercount = event.getGuild().getMembers();
		ArrayList<String> memberNames = new ArrayList<String>();
		
		for(int i = 0; i < membercount.size(); i++) {
			memberNames.add(i, membercount.get(i).getUser().toString().substring(2, membercount.get(i).getUser().toString().indexOf("(")));
		}
		String[] memberList = new String[membercount.size()];
		for(int i = 0; i < membercount.size(); i++) {
			memberList[i] = memberNames.get(i);
		}
		if(args[0].equalsIgnoreCase(prefix + "funnymemberlist") /*&& (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().isOwner())*/) {
			String memberListForChannel = "";
			for(int i = 0; i < memberList.length; i++) {
				if(membercount.get(i).hasPermission(Permission.ADMINISTRATOR) && !membercount.get(i).isOwner() && !membercount.get(i).getId().equals("508100678758170644")) {
					memberListForChannel += "Admin: " + memberList[i] + "#" + membercount.get(i).getUser().getDiscriminator() + "(" + membercount.get(i).getUser().getId() + ")" + "\n";
				} else if(membercount.get(i).isOwner()) {
					memberListForChannel += "Ring Leader: " + memberList[i] + "#" + membercount.get(i).getUser().getDiscriminator() + "(" + membercount.get(i).getUser().getId() + ")" + "\n";
				} else if(membercount.get(i).getId().equals("508100678758170644")) {
					  memberListForChannel += "Racist: " + memberList[i] + "#" + membercount.get(i).getUser().getDiscriminator() + "(" + membercount.get(i).getUser().getId() + ")" + "\n";	
				} else {
					memberListForChannel += " \"Regular\" people: " + memberList[i] + "#" + membercount.get(i).getUser().getDiscriminator() + "(" + membercount.get(i).getUser().getId() + ")" + "\n";
				}
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Members in " + event.getGuild().getName());
			eb.setDescription(memberListForChannel);
			scts.sendMessage(event, eb.build());
		}
		
		if(args[0].equalsIgnoreCase("fuckyou")) {
			event.getGuild().moveVoiceMember(event.getGuild().getMemberById("256920677385371649"), event.getGuild().getVoiceChannelById("932093403322273793")).queue();
		}
		
		List<Attachment> att = event.getMessage().getAttachments();
		for(int i = 0; i < att.size(); i++) {
			if(att.get(i).getFileName().equals("plish.gif") || att.get(i).getFileName().equals("image0-2.gif")) {
				scts.deleteMessage(event);
			}
		}
		for(int i = 0; i < att.size(); i++) {
			if(event.getChannel().getId().equals("956785211281113169") && !att.get(i).getFileExtension().equals("gif")) {
				scts.deleteMessage(event);
			}
		}
		if(args[0].equalsIgnoreCase("trolenames") && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			Member self = event.getMember();
			Random randNum = new Random();
		    Set<Integer>set = new LinkedHashSet<Integer>();
		    while (set.size() < event.getGuild().getMemberCount()) {
		       set.add(randNum.nextInt(event.getGuild().getMemberCount()) + 1);
		    }
		    ArrayList<Integer> arr = new ArrayList<Integer>();
		    for(Iterator<Integer> it = set.iterator(); it.hasNext();) {
		    	arr.add(it.next());
		    }
		    int i = 0;
			for(Member member : event.getGuild().getMembers()) {
				if(self.canInteract(member) && i < arr.size()) {
					event.getGuild().modifyNickname(member, getName()).queue();
					i++;
				}
			}
		} else if(args[0].equalsIgnoreCase("trolenames") && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			scts.sendMessage(event, "You cannot do that idiot", true);
		}
		if(args[0].equalsIgnoreCase("geo") && isServer) {
			scts.deleteMessage(event);
			scts.sendMessage(event, "<@256920677385371649> doo doo shitter is a bad game!", false);
		}
		if(args[0].equalsIgnoreCase("Say") && args[1].equalsIgnoreCase("the") && args[2].equalsIgnoreCase("line,") && args[3].equalsIgnoreCase("bulbjak!")) {
			if(event.getGuild().getName().equals("Femboy Sorority")) {
				scts.sendMessage(event, "Learn to LIGHTEN up! <:bulbjak:954624531375460393>", true);
			}
		}
		
		if(args[0].equalsIgnoreCase("among") && args[1].equalsIgnoreCase("us")) {
			scts.sendMessage(event, "sus", true);
		}
		
		if(isServer && args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("activate") && args[2].equalsIgnoreCase("iguana")) {
			scts.sendMessage(event, "https://cdn.discordapp.com/attachments/325351648849428480/901152594729259008/Iguana.mp4", true);
		}
		if(isServer && args[0].equalsIgnoreCase("computer") && args[1].equalsIgnoreCase("deactivate") && args[2].equalsIgnoreCase("iguana")) {
			scts.sendMessage(event, "https://cdn.discordapp.com/attachments/325351648849428480/901152499686330428/Iguana.mp4", true);
		}

		if(args[0].equalsIgnoreCase("fnaf")) {
			scts.sendMessage(event, "Connection terminated. I'm sorry to interupt you Elizabeth, if you still even remember that name, but I'm afraid you've been misinformed. You're not here to recieve a gift, nor have you been called here by the individual you assume, although you have indeed been called. You have all been called here, into a labyrinth of sounds and smells, misdirection and misfortune. A labirynth with no exit, a maze with no prize. You don't even realize that you are trapped. Your lust for blood has driven you in endless circles, chasing the cries of children in some unseen chamber always seeming so near, yet somehow out of reach. But you will never find them, none of you will. This is where your story ends.\r\n"
					+ "\r\n"
					+ "And to you, my brave volunteer, who somehow found this job listing not intended for you, although there was a way out planned for you, I have a feeling that's not what you want. I have a feeling that you are right where you want to be. I am remaining as well. I am nearby. This place will not be remembered and the memory of everything that started this can finally begin to fade away, as the agony of every tragedy should. And to you monsters trapped in the corridors, be still, and give up your spirits. They don't belong to you. For most of you, I believe there is peace and perhaps more waiting for you after the smoke clears. Although for one of you, the darkest pit of hell has opened to swallow you whole, so don't keep the devil waiting old friend.\r\n"
					+ "\r\n", false);
			scts.sendMessage(event, "My daughter, if you can hear me, I knew you would return as well. It's in your nature to protect the innocent. I'm sorry that on that day, the day you were shut out and left to die, no one was there to lift you up into their arms, the way you lifted others into yours. And then, what became of you? I should have known you wouldn't be contempt to just disappear, not my daughter. I couldn't save you then, so let me save you now.\r\n"
					+ "\r\n"
					+ "It's time to rest. For you, and those you have carried in your arms. This ends, for all of us. End communication.", false);
		}
		if(isServer) {
			if(args[0].equalsIgnoreCase("jangosong")) {
				scts.sendMessage(event, "When I wake up, well I know I'm gonna be,\r\n"
						+ "I'm gonna be the man who wakes up next to you\r\n"
						+ "When I go out, yeah I know I'm gonna be\r\n"
						+ "I'm gonna be the man who goes along with you\r\n"
						+ "When I get drunk, well I know I'm gonna be\r\n"
						+ "I'm gonna be the man who gets drunk next to you\r\n"
						+ "And when I haver, hey I know I'm gonna be\r\n"
						+ "I'm gonna be the man who's havering to you\r\n"
						+ "And I would roll 500 miles\r\n"
						+ "And I would roll 500 more\r\n"
						+ "Just to be the man who rolls a thousand miles\r\n"
						+ "To fall down at your door\r\n"
						+ "When I'm working, yes I know I'm gonna be\r\n"
						+ "I'm gonna be the man who's working hard for you\r\n"
						+ "And when the money, comes in for the work I do\r\n"
						+ "I'll pass almost every penny on to you\r\n"
						+ "When I come home(When I come home), well I know I'm gonna be\r\n"
						+ "I'm gonna be the man who comes back home to you\r\n"
						+ "And when I grow old, well I know I'm gonna be\r\n"
						+ "I'm gonna be the man who's growing old with you\r\n"
						+ "But I would roll 500 miles\r\n"
						+ "And I would roll 500 more\r\n"
						+ "Just to be the man who roles a thousand miles\r\n"
						+ "To fall down at your door\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da Da Da Dun Diddle Un Diddle Un Diddle Uh Da Da\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da Da Da Dun Diddle Un Diddle Un Diddle Uh Da Da\r\n"
						+ "When I'm lonely, well I know I'm gonna be\r\n"
						+ "I'm gonna be the man who's lonely without you\r\n"
						+ "And when I'm dreaming, well I know I'm gonna dream\r\n"
						+ "I'm gonna Dream about the time when I'm with you\r\n"
						+ "When I go out(When I go out), well I know I'm gonna be\r\n"
						+ "I'm gonna be the man who goes along with you\r\n"
						+ "And when I come home(When I come home), yes I know I'm gonna be\r\n"
						+ "I'm gonna be the man who comes back home with you\r\n"
						+ "I'm gonna be the man who's coming home with you\r\n"
						+ "And I would roll 500 miles\r\n", false);
				scts.sendMessage(event, "And I would roll 500 more\r\n"
						+ "Just to be the man who rolled a thousand miles\r\n"
						+ "To fall down at your door\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da Da Da Dun Diddle Un Diddle Un Diddle Uh Da Da\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da Da Da Dun Diddle Un Diddle Un Diddle Uh Da Da\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da Da Da Dun Diddle Un Diddle Un Diddle Uh Da Da\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da da da (da da da)\r\n"
						+ "Da Da Da Dun Diddle Un Diddle Un Diddle Uh Da Da\r\n"
						+ "And I would walk 500 miles\r\n"
						+ "And I would roll 500 more\r\n"
						+ "Just to be the man who rolled a thousand miles\r\n"
						+ "To fall down at your door\r\n"
						+ "Eh\r\n"
						+ "Don't like it\r\n"
						+ "I love the Welsh", false);
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "oosted?")) {
			if(event.getGuild().getBoostCount() == 0) {
				scts.sendMessage(event, "not oosted <:cubic:941574305748242493>", false);
			} else if(event.getGuild().getBoostCount() > 0) {
				scts.sendMessage(event, "oosted (" + event.getGuild().getBoostCount() + ") times! <:aaaaaaaaaaahhhh:932080598066024448>", false);
			}
		}
		if(args[0].equalsIgnoreCase("sundowner")) {
			scts.sendMessage(event, "<:sundowner:941904862805889045>", false);
		}
		int len = args.length; //creates variable to test for args.length so i dont have to use it every time
		if(len == 1 && args[0].equalsIgnoreCase(prefix + "coinflip")) {
			scts.sendMessage(event, "tails", false);
			len = -1; //sets len to -1 to break out of the if statement so it doesnt repeat forever
		} else if(args.length > 1) {
			int ascii = 0; //declaring variable for ascii
			if(args[0].equalsIgnoreCase(prefix + "coinflip")) {
				try {
					if ((Integer) Integer.parseInt(args[1]) instanceof Integer) { //tests if the second argument of the command contains an integer
						try {
							if(Integer.parseInt(args[1]) < Integer.MAX_VALUE) { //tests if it is below 32-bit integer limit, but JDA is weird and puts any number greater as a string, might get rid of this
								scts.sendMessage(event, "Heads: 0 \nTails: " + Integer.parseInt(args[1]), false);
							}
						}catch(Exception e) {
							scts.sendMessage(event, "Heads: 0 \nTails: " + args[1], false); //if, by some magic fuckery, it isnt an int it will display as a string. probably useless but might as well be safe
						}
						
					} 
				}catch(NumberFormatException e) {
					String arg = args[1]; //gets the second argument as a string
					int iterator = arg.length();
					if(args[1] instanceof String) { //tests if argument is a string object
						for(int i = 0; i < iterator - 1; i++) {
							ascii += (int) arg.charAt(i); //adds ascii values of each character in the string together
						}
						scts.sendMessage(event, "Heads: 0 \nTails: " + ascii, false);
					} 
				}
			}

			 
			if(args[0].equalsIgnoreCase(prefix + "realcoinflip")) {
				String memberGuess = args[1];
				int flip = ThreadLocalRandom.current().nextInt(0, 2);
				String heads = "";
				String tails = "";
				boolean win = false;
				if(flip == 0) {
					heads = "heads";
				} else if(flip == 1) {
					tails = "tails";
				}
				if(memberGuess.equals(heads) || memberGuess.equals(tails)) {
					win = true;
				} else if(!memberGuess.equals(heads) || !memberGuess.equals(tails)) {
					win = false;
				}
				if(win) {
					scts.sendMessage(event, "Result is: " + heads + "" + tails + " you win!", false);
				} else {
					scts.sendMessage(event, "Result is: " + heads + "" + tails + " you lose <:METH:932089247350013972>", false);
				}
			}
		} 
		if(args[0].equalsIgnoreCase("disgusting")) {
			scts.sendMessage(event, "https://cdn.discordapp.com/attachments/931616914227208203/956032356077146162/20220322_223021.jpg", false);
		}
		if(args[0].equalsIgnoreCase("Fortnite") && args[1].equalsIgnoreCase("battle") && args[2].equalsIgnoreCase("pass")) {
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setTitle("I just shit out my ass");
			embed.setImage("https://cdn.discordapp.com/attachments/939768536295952507/956249267104780308/fortnite.gif");
			scts.sendMessage(event, embed.build());
			
		}
		String[] kiss = {"kiss", "kissing", "ki55", "ki55ing", "k1551ng", "ki551ng", "k155", "k1ss", "k1ssing", "ki5sing", "kis5ing", "k1s5ing", "k15sing"};
		for(int i = 0; i < args.length; i++) {
			for(int j = 0; j < kiss.length; j++) {
				if(args[i].equalsIgnoreCase(kiss[j])) {
					scts.sendMessage(event, "https://cdn.discordapp.com/attachments/939768536295952507/956259632299528192/yoad.png", false);
					break;
				}
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("Big") && args[i + 1].equalsIgnoreCase("Blungus") && i <= args.length) {
				scts.sendMessage(event, "<:blungus:953389471917826098>", false);
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("jerma")) {
				scts.react(event, "jerm:955920874349932624");
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("ena")) {
				scts.react(event, "enar:942667786612781066");
			}
		}
		
		if(args[0].equalsIgnoreCase(prefix + "isPrimitiveType")) {
			try {
				if((Integer) Integer.parseInt(args[1]) instanceof Integer) {
					scts.sendMessage(event, "number", false);
				}
			}catch(NumberFormatException e) {
				if(args[1] instanceof String) {
					scts.sendMessage(event, "letters", false);
				}
			}catch(IndexOutOfBoundsException x) {
				scts.sendMessage(event, "null", false);
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("sayaarat")) {
				try {
					scts.deleteMessage(event);
					scts.sendMessage(event, "https://cdn.discordapp.com/attachments/945260997453697034/956375825995948143/ezgif.com-gif-maker_12.gif", false);
					i = args.length;
				}catch(InsufficientPermissionException e) {
					scts.sendMessage(event, "https://cdn.discordapp.com/attachments/945260997453697034/956375825995948143/ezgif.com-gif-maker_12.gif", false);
					i = args.length;
				}
			}
		}
		if(args[0].equalsIgnoreCase(prefix + "memberlist")) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Members of " + event.getGuild().getName());
			event.getGuild().getMembers().parallelStream().map(member -> member.getUser().getName() + "#" + member.getUser().getDiscriminator() + " (" + member.getUser().getId()).reduce((s, s2) -> s + ")\n" + s2).ifPresent(eb::setDescription);
			scts.sendMessage(event, eb.build());
		}
		if(args[0].equalsIgnoreCase("lungus")) {
			scts.sendMessage(event, "https://cdn.discordapp.com/attachments/945260997453697034/956772642617167882/Screenshot_358.png", false);
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("perchance")) {
				scts.sendMessage(event, "https://cdn.discordapp.com/attachments/415604103020937218/959207715694080031/IMG_5913.png", false);
			}
		}
		
		if(args[0].equalsIgnoreCase(prefix + "differentiate")) {
			int cons = Integer.parseInt(args[1].substring(0, 1));
			String var = "x";
			int exp = Integer.parseInt(args[1].substring(args[1].length() - 1, args[1].length()));
			int consDeriv = exp * cons;
			int expDeriv = exp - 1;
			if(expDeriv == 1) {
				scts.sendMessage(event, consDeriv + var, false);
			} else if(expDeriv > 1) {
				scts.sendMessage(event, consDeriv + var + "^" + expDeriv, false);
			}
		}
		
		File[] imgs = { new File("spunch.png"), new File("code.png"), new File("cornic.jpg") };
		
		if(args[0].equalsIgnoreCase(prefix + "rgb")) {
			int rand = ThreadLocalRandom.current().nextInt(0, imgs.length);
			try {
				BufferedImage bfdImg = ImageIO.read(imgs[rand]);
				byte[] rgb = (byte[])bfdImg.getRaster().getDataElements(0, 0, bfdImg.getWidth(), bfdImg.getHeight(), null);
				int vals = 0;
				for(byte val : rgb) {
					vals += Math.abs(val);
				}
				scts.sendMessage(event, String.valueOf(vals), false);
				scts.sendMessage(event, imgs[rand]);
			} catch (IOException e) {
				scts.sendMessage(event, "broekd", false);
			} catch(IndexOutOfBoundsException x) {
				scts.sendMessage(event, "broked", false);
			}
		}
		ArrayList<String> bannedWords = new ArrayList<String>();
		File f = new File("C:/Users/mmmmm/Desktop/botgifs/bannedwords.txt");
		Member owner = event.getGuild().getOwner();
		Member dig = event.getGuild().getMemberById("695688150466428989");
		boolean isOwnerOnline = owner.getOnlineStatus().equals(OnlineStatus.ONLINE);
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine()) {
				bannedWords.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("AcornPro")) {
				scts.sendMessage(event, "https://cdn.discordapp.com/attachments/945260997453697034/959682751664115742/IMG_20220402_001615_141.jpg", false);
			}
		}
		for(int i = 0; i < args.length; i++) {
			if(event.getAuthor().getId().equals("530269428185825290") && args[i].equalsIgnoreCase("kys")) {
				scts.react(event, "🤓");
			}
		}
		for(int i = 0; i < args.length; i++) {
			for(String m : bannedWords) {
				if(args[i].equalsIgnoreCase(m) && !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					scts.deleteMessage(event);
					event.getMember().ban(100, "Racial Slur").queue();
					scts.sendMessage(event, event.getAuthor().getAsMention() + " has been banned.", false);
				} else if(args[i].equalsIgnoreCase(m) && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					Member alertee = isOwnerOnline ? owner : dig;
					scts.deleteMessage(event);
					alertee.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(event.getAuthor().getAsMention() + " said " + m)).queue();
				}
			}
			
		}
		try {
			if(event.getMessage().getAttachments().get(0).getFileName().equals("image0-1-1.gif") && event.getMessage().getAttachments() != null) {
				scts.sendMessage(event, "https://media.discordapp.net/attachments/505823512754257920/844031336599519292/image0-1-1.gif", false);
			}
		}catch(Exception e) {
			return;
		}
		
	}
	
	
	public String getName() {
		return this.nameChange;
	}
	
	public int joeCheck(int joeCount) {
		if(joeCount >= 10) {
			try {
				joeFile.writeFile(joeCount + randCounter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return joeCount;
	}
	public void killJoeBiden(GuildMessageReceivedEvent event) {
		String counter = String.valueOf(joeCount);
		joeCount++;
		try {
			joeFile.writeFile(joeCount);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			counter = joeFile.readFile(joeFile.joe);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(joeCount <= 10 && joeCount > 1) {
			event.getChannel().sendTyping().delay(Duration.ofSeconds(5)).queue();
			event.getChannel().sendMessage("I have killed Joe Biden, here is the video.").queue();
			event.getChannel().sendFile(new File("C:/Users/mmmmm/Desktop/botgifs/biden.gif")).queue();
			event.getChannel().sendMessage("Joe Biden has been killed " + counter + " times.").queue();
		} else if (joeCount == 1) {
			event.getChannel().sendTyping().delay(Duration.ofSeconds(5)).queue();
			event.getChannel().sendMessage("I have killed Joe Biden, here is the video.").queue();
			event.getChannel().sendFile(new File("C:/Users/mmmmm/Desktop/botgifs/biden.gif")).queue();
			event.getChannel().sendMessage("Joe Biden has been killed " + counter + " time.").queue();
		}
		
		else if(joeCheck(joeCount) == maximumJoe) {
			event.getChannel().sendMessage("Oops, Joe Biden is now perma dead!").queue();
		}
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(f, true));
			pw.println("Joe Biden death count raised to " + joeCount);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setName(String name) {
		this.nameChange = name;
	}
}

