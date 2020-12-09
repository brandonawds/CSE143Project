package cse143.Impact;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
	// onGuildMessageReceived looks for commands/events and will send things back to the channel
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Getting the argument/commands
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// If !info, bot will send back information about itself
		if (args[0].equalsIgnoreCase(Main.prefix + "info")) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("🎰 XXImpact Bot Information");
			info.setDescription("XX Impact (real name TBD) is a Discord bot based on the gacha game system. A player pays a specific amount of currency to “spin” the gacha, and then receives a random item or character. Don’t worry, we won’t actually make you spend real money on waifus/husbandos. It will let Discord users join the game through inputted commands allowing them to gain points, spend those points on rolls for characters, keep track of their roster & character stats; further allowing them to potentially battle other friends. ");
			//info.addField("Creators", "Brandon Ly, Vivian Nguyen, Vincent Le", false);
			info.setColor(0xa2defc);
			info.setFooter("Created by Brandon Ly, Vivian Nguyen, Vincent Le", event.getMember().getUser().getAvatarUrl());
			
			// Shows that bot is typing in discord
			event.getChannel().sendTyping().queue();
			// Builds and sends the message
			event.getChannel().sendMessage(info.build()).queue();
			// Clears the embed message for storage sake
			info.clear();
		}
		
		// If !ayy command, bot will respond with lmao
		if (args[0].equalsIgnoreCase(Main.prefix + "ayy")) {
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("lmao").queue();
		}
	}
}
