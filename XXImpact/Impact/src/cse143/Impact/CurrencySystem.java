package cse143.Impact;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Currency System creates virtual currency for users to spend on prizes
public class CurrencySystem extends ListenerAdapter {
	// Store user currency and cooldown timers
	public static HashMap<Member, Integer> playerCurrency = new HashMap<>();
	// Stores the time left each player has before they can use the !getmoney command again
	private HashMap<Member, Integer> playerTimer = new HashMap<>();
	// The cooldown time for using the !getmoney command
	private static final int TIMER = 10;
	// The amount of points you get from !getmoney
	public static final int POINTS = 1; 
	
	// Reads discord messages and responds if they are commands.
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// If !money, bot will send showing the users currency
		if (args[0].equalsIgnoreCase(Main.PREFIX + "points")) {
			if(!playerCurrency.containsKey(event.getMember())) {
				setPlayerCurrency(event.getMember(), 0);
			}
			event.getChannel().sendMessage("You have " + getPlayerCurrency(event.getMember()) + " points").queue();
		}
		
		// If !cd, bot will send remaining cooldown timer
		if (args[0].equalsIgnoreCase(Main.PREFIX + "cd")) {
			if(playerTimer.containsKey(event.getMember())) {
				event.getChannel().sendMessage(getPlayerTimer(event.getMember()) + " seconds").queue();
			} else {
				event.getChannel().sendMessage("You can !getPoints now!").queue();
			}
		}
		
		// If !getmoney, bot will add currency to user if user does not have a cooldown
		if (args[0].equalsIgnoreCase(Main.PREFIX + "getPoints")) {
			if (canGetCurrency(event.getMember())) {
				addCurrency(event.getMember()); 
				setPlayerTimer(event.getMember(), TIMER);
				event.getChannel().sendMessage("Added " + POINTS + " point(s)!").queue();
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
		setPlayerCurrency(member, getPlayerCurrency(member) + POINTS);
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

