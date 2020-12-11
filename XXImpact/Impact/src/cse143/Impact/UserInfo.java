package cse143.Impact;

import java.util.HashMap;
import java.util.Set;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

// Creates a profile for the user mentioned in the command
// Profiles include: User name, User avatar, User's prizes, and User's current point balance
public class UserInfo extends Roll {
	// Stores the prizes for each player
	HashMap<Member, Set<Prize>> playerPrizes = Roll.playerPrizes;
	// Stores the currency for each player
	HashMap<Member, Integer> playerCurrency = CurrencySystem.playerCurrency;
	
	// Listens to discord messages and responds to valid commands
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// Sends user info of a mentioned member
		if (args[0].equalsIgnoreCase(Main.prefix + "userInfo")) {
			if (event.getMessage().getMentionedMembers().size() == 0) {
				event.getChannel().sendMessage("Must mention member. Ex: !userInfo @<member>").queue();
			} else {
				// Gets the mentioned member
				Member name = event.getMessage().getMentionedMembers().get(0);
				EmbedBuilder user = new EmbedBuilder();
				user.setAuthor(name.getEffectiveName(),name.getUser().getAvatarUrl(), name.getUser().getAvatarUrl());
				user.setColor(0xa2defc);
				
				// Check prizes
				if (playerPrizes.containsKey(name)) { 
					user = addPrizeFields(user, name, false);
				} else {
					event.getChannel().sendMessage("No Snails").queue();
				}
				
				// Check Balance
				if (playerCurrency.containsKey(name)) {
					user.addField("Point Balance: ", String.valueOf(playerCurrency.get(name)), false);
				} else {
					event.getChannel().sendMessage(name.getEffectiveName() + " is broke :(").queue();
				}
				event.getChannel().sendMessage(user.build()).queue();
				user.clear();
			}
		}
	}
}
