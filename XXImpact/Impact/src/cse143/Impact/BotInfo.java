package cse143.Impact;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Sends information about SnailImpact
public class BotInfo extends ListenerAdapter {
	
	// onGuildMessageReceived looks for commands/events and will send things back to the channel
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Getting the argument/commands
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// If !info, bot will send back information about itself
		if (args[0].equalsIgnoreCase(Main.PREFIX + "info")) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Snail Impact Bot Information");
			info.setDescription("Snail Impact is a Discord bot based on the gacha game system. A player pays a specific amount of currency to “spin” the gacha, "
					+ "and then receives a random snail. Don’t worry, we won’t actually make you spend real money on snails. It will let Discord "
					+ "users join the game through inputted commands allowing them to gain points, spend those points on rolls for snails and keep track of their roster");
			info.setColor(0xa2defc);
			info.setFooter("Created by Brandon Ly, Vivian Nguyen, Vincent Le", event.getMember().getUser().getAvatarUrl());
			
			// Shows that bot is typing in discord
			event.getChannel().sendTyping().queue();
			// Builds and sends the message
			event.getChannel().sendMessage(info.build()).queue();
			// Clears the embed message for storage sake
			info.clear();
		}
	}
}
