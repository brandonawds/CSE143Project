package cse143.Impact;

// Imports
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

// Main creates the discord bot with a unique token, listening for commands in the chat
public class Main {
	public static JDA jda;
	// The prefix to the commands typed in discord
	public static final String PREFIX = "!";
	
	// Main Method
	public static void main(String[] args) throws LoginException {
		// Creating the bot with the token they gave me in discord dev portal
		jda = JDABuilder.createDefault(<TOKEN>).build();
		
		// Setting bot activity 
		jda.getPresence().setActivity(Activity.playing("Studying for CSE143"));
		
		// Roll a new prize rolling system
		Roll roll = new Roll();
		
		// UserInfo creates a user profile for each user 
		UserInfo info = new UserInfo();
		
		// CurrencySystem creates the currency system and sets the cooldown timer
		CurrencySystem system = new CurrencySystem();
		system.startTimer();
		
		// Telling bot to listen to commands
		jda.addEventListener(new BotInfo());
		jda.addEventListener(system);
		jda.addEventListener(roll);
		jda.addEventListener(info);
		
	}
}
