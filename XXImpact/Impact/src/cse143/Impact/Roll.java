package cse143.Impact;

import java.util.HashMap;
import java.util.HashSet;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.Random;
import java.util.Set;

// Roll gives a random snail/prize to the user and costs a certain amount of points.
public class Roll extends CurrencySystem {
	// Stores the prizes each player currently has
	public static HashMap<Member, Set<Prize>> playerPrizes = new HashMap<>();
	// Stores user currency
	HashMap<Member, Integer> playerCurrency = CurrencySystem.playerCurrency;
	// The amount of points it costs to roll
	private static final int COST = 1;
	
	// Listens to discord chat and responds when a valid command is sent
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Getting the argument/commands
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// Rolls random prize from prizeNames
		// User needs to have at least 1 point to roll
		// Adds won prize to that user's list of prizes
		if (args[0].equalsIgnoreCase(Main.PREFIX + "roll")) {
			if(!playerCurrency.containsKey(event.getMember())) {
				event.getChannel().sendMessage("You need points to roll. Try command: !getmoney").queue();
			} else if(getPlayerCurrency(event.getMember()) > 0) {
				Prize prize = rollRandomPrize();
				setPlayerCurrency(event.getMember(), getPlayerCurrency(event.getMember()) - COST);
				addPrize(event.getMember(), prize);
				// Sends update message to user as an embed
				EmbedBuilder newRoll = new EmbedBuilder();
				newRoll.setColor(0xa2defc);
				newRoll.setAuthor("You Rolled...");
				newRoll.addField(prize.name, "Rarity: " + prize.rarity, false);
				newRoll.setImage(prize.url);
				newRoll.addField("Balance: ", String.valueOf(getPlayerCurrency(event.getMember())), false);
				event.getChannel().sendMessage(newRoll.build()).queue();
				// Clears the embed message for storage sake
				newRoll.clear();
			} else {
				event.getChannel().sendMessage("You need at least " + COST + " point to roll").queue();
			}
		}
		
		// Creates an embed of your prizes
		if (args[0].equalsIgnoreCase(Main.PREFIX + "mySnails")) {
			if (!playerPrizes.containsKey(event.getMember())) {
				event.getChannel().sendMessage("You have no snails").queue();
			} else {
				EmbedBuilder prizesInfo = new EmbedBuilder();
				prizesInfo.setTitle("Your Snails:");
				prizesInfo.setColor(0xa2defc);
				prizesInfo = addPrizeFields(prizesInfo, event.getMember(), false);
				// Builds and sends the message
				event.getChannel().sendMessage(prizesInfo.build()).queue();
				// Clears the embed message for storage sake
				prizesInfo.clear();
			}
		}		
	}
	
	// Adds a field to the embed with all your prizes
	public static EmbedBuilder addPrizeFields(EmbedBuilder builder, Member member, boolean isRoll) {
		Set<Prize> prizeSet = getPrizes(member);
		for (Prize prize : prizeSet) {
			builder.addField(prize.name, "Rarity: " + prize.rarity, false);
			if (isRoll) {
				builder.setImage(prize.url);
			}
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
			return new Prize("Garden Snail", "https://static.wikia.nocookie.net/adventuretimewithfinnandjake/images/0/07/Waving_Snail.png/revision/latest?cb=20120729225549");
		} else if (num >= 5 && num <= 7) {
			return new Prize("Escargoon", "https://static.wikia.nocookie.net/kirby/images/a/a3/HnK_Escargoon_2.png/revision/latest/top-crop/width/300/height/300?cb=20130402224843&path-prefix=en");
		} else if (num >= 8 && num >= 9) {
			return new Prize("Turbo", "https://vignette.wikia.nocookie.net/turbo-dreamworks/images/5/51/Theo.png/revision/latest/top-crop/width/300/height/300?cb=20130707190818");
		} else {
			return new Prize("Gary", "https://static.wikia.nocookie.net/p__/images/9/9e/Gary_looking_up_stock_art.png/revision/latest?cb=20190526113033&path-prefix=protagonist");
		}
			
	}
	
	// Constructs a Prize object with name as parameter
	public static class Prize {
		// Name of prize
		public String name;
		// Rarity of prize
		public String rarity;
		// URL to prize image
		public String url;
		
		// Constructs a new Prize with the given name and image url
		// Sets the rarity of the prize based on the name
		public Prize(String name, String url) {
			this.url = url;
			this.name = name;
			if (name.equals("Garden Snail")) {
				this.rarity = "Common";
			} else if (name.equals("Escargoon")) {
				this.rarity = "Rare";
			} else if (name.equals("Turbo")) {
				this.rarity = "Very Rare";
			} else {
				this.rarity = "Legendary";
			}
		}
	}
}

