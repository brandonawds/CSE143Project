package cse143.Impact;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

//import cse143.Impact.Roll.Prize;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CurrencySystem extends ListenerAdapter {
	// Store user currency and cooldown timers
	public static HashMap<Member, Integer> playerCurrency = new HashMap<>();
	HashMap<Member, Integer> playerTimer = new HashMap<>();
	//HashMap<Member, Set<Prize>> playerPrizes = new HashMap<>();
	String[] prizeNames = {"Pidgey", "Growlithe", "Snorlax", "MewTwo"};
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// If !money, bot will send showing the users currency
		if (args[0].equalsIgnoreCase(Main.prefix + "money")) {
			if(!playerCurrency.containsKey(event.getMember())) {
				setPlayerCurrency(event.getMember(), 0);
			}
			event.getChannel().sendMessage("You have " + getPlayerCurrency(event.getMember())).queue();
		}
		
		// If !cd, bot will send remaining cooldown timer
		if (args[0].equalsIgnoreCase(Main.prefix + "cd")) {
			if(playerTimer.containsKey(event.getMember())) {
				event.getChannel().sendMessage(getPlayerTimer(event.getMember()) + "seconds").queue();
			} else {
				event.getChannel().sendMessage("You can !getmoney now!").queue();
			}
		}
		
		// If !getmoney, bot will add currency to user if user does not have a cooldown
		if (args[0].equalsIgnoreCase(Main.prefix + "getmoney")) {
			if (canGetCurrency(event.getMember())) {
				addCurrency(event.getMember());
				setPlayerTimer(event.getMember(), 10);
				event.getChannel().sendMessage("Added 1 currency!").queue();
			} else {
				event.getChannel().sendMessage("You must wait " + getPlayerTimer(event.getMember()) + " seconds").queue();
			}
		}
	}
		
	// Sets users currency
	public static void setPlayerCurrency(Member member, int num) {
		playerCurrency.put(member, num);
	}
	
	// Sets users cooldown timer
	public void setPlayerTimer(Member member, int num) {
		playerTimer.put(member, num);
	}
	
	// Returns users cooldown timer
	private int getPlayerTimer(Member member) {
		return playerTimer.get(member);
	}
	
	// Returns users curency
	public static int getPlayerCurrency(Member member ) {
		return playerCurrency.get(member);
	}
	
	// Adds currency to user
	private void addCurrency(Member member) {
		if (!playerCurrency.containsKey(member)) {
			setPlayerCurrency(member, 0);
		}
		setPlayerCurrency(member, getPlayerCurrency(member) + 1);
	}
	
	// Returns if user has a cooldown timer
	private boolean canGetCurrency(Member member) {
		if (!playerTimer.containsKey(member)) {
			return true;
		} else {
			return false;
		}
	}
	
	// starts timer to countdown user cooldown, not working correctly?
	public void startTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				for(Member member : playerTimer.keySet()) {
					setPlayerTimer(member, getPlayerTimer(member) - 1);
					if (getPlayerTimer(member) == 0) {
						playerTimer.remove(member);
					}
				}
			}
		};
		timer.schedule(task, 1000, 1000);
	}
}

