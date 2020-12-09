package cse143.Impact;

import java.util.HashMap;
import java.util.Set;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UserInfo extends Roll {
	HashMap<Member, Set<Prize>> playerPrizes = Roll.playerPrizes;
	HashMap<Member, Integer> playerCurrency = CurrencySystem.playerCurrency;
	
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
				//user.setAuthor(name.getEffectiveName(), name.getUser().getEffectiveAvatarUrl());
				user.setTitle(name.getEffectiveName(), name.getUser().getAvatarUrl());
				user.setColor(0xa2defc);
				// If this player has prizes, add prizes
				if (playerPrizes.containsKey(name)) { 
					user = addPrizeFields(user, name);
				}
				// If this player has currency, add
				if (playerCurrency.containsKey(name)) {
					user.addField("Point Balance: ", String.valueOf(playerCurrency.get(name)), false);
				}
				
				event.getChannel().sendMessage(user.build()).queue();
				user.clear();
			}
		}
	}
}
