package cse143.Impact;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.Random;
import java.util.Set;

public class Roll extends CurrencySystem {
	public static HashMap<Member, Set<Prize>> playerPrizes = new HashMap<>();
	HashMap<Member, Integer> playerCurrency = CurrencySystem.playerCurrency;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Getting the argument/commands
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// Rolls random prize from prizeNames
		// User needs to have at least 1 point to roll
		// Adds won prize to that user's list of prizes
		if (args[0].equalsIgnoreCase(Main.prefix + "roll")) {
			if(!playerCurrency.containsKey(event.getMember())) {
				event.getChannel().sendMessage("You need points to roll. Try command: !getmoney").queue();
			} else if(getPlayerCurrency(event.getMember()) > 0) {
				Prize prize = rollRandomPrize();
				setPlayerCurrency(event.getMember(), getPlayerCurrency(event.getMember()) - 1);
				addPrize(event.getMember(), prize);
				// Sends update message to user
				event.getChannel().sendMessage("Cost: 1 point").queue();
				event.getChannel().sendMessage("You got " + prize.name).queue();
				event.getChannel().sendMessage("Balance: " + getPlayerCurrency(event.getMember()) + " points").queue();
			} else {
				event.getChannel().sendMessage("You need at least 1 point to roll").queue();
			}
		}
		
		// Creates an embed of your prizes
		if (args[0].equalsIgnoreCase(Main.prefix + "myPrizes")) {
			EmbedBuilder prizesInfo = new EmbedBuilder();
			prizesInfo.setTitle("Your Prizes:");
			prizesInfo.setColor(0xa2defc);
			prizesInfo = addPrizeFields(prizesInfo, event.getMember());
			// Builds and sends the message
			event.getChannel().sendMessage(prizesInfo.build()).queue();
			// Clears the embed message for storage sake
			prizesInfo.clear();
		}		
	}
	
	public static EmbedBuilder addPrizeFields(EmbedBuilder builder, Member member) {
		Set<Prize> prizeSet = getPrizes(member);
		for (Prize prize : prizeSet) {
			builder.addField(prize.name, "Rarity: " + prize.rarity, false);
		}
		return builder;
	}
	
	// Add to prizes
	private void addPrize(Member member, Prize prize) {
		if (!playerPrizes.containsKey(member)) {
			playerPrizes.put(member, new HashSet<Prize>());
		}
		playerPrizes.get(member).add(prize);
	}
	
	// Get prize list
	private static Set<Prize> getPrizes(Member member) {
		return playerPrizes.get(member);
	}
	
	// Rolls random prize
	private Prize rollRandomPrize() {
		Random rand = new Random();
		int num = rand.nextInt(10) + 1;
		if (num >= 1 && num <= 4) {
			return new Prize("Pidgey");
		} else if (num >= 5 && num <= 7) {
			return new Prize("Growlithe");
		} else if (num >= 8 && num >= 9) {
			return new Prize("Snorlax");
		} else {
			return new Prize("MewTwo");
		}
			
	}
	
	// Constructs a Prize object with name as parameter
	public static class Prize {
		public String name;
		public String rarity;
		
		public Prize(String name) {
			this.name = name;
			if (name.equals("Pidgey")) {
				this.rarity = "Common";
			} else if (name.equals("Growlithe")) {
				this.rarity = "Rare";
			} else if (name.equals("Snorlax")) {
				this.rarity = "Very Rare";
			} else {
				this.rarity = "Legendary";
			}
		}
	}
}
