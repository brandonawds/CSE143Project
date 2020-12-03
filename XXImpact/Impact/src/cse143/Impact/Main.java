package cse143.Impact;

// Imports
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main {
	public static JDA jda;
	public static String prefix = "!";
	
	
	// Main Method
	public static void main(String[] args) throws LoginException {
		// Creating the bot with the token they gave me in discord dev portal
		jda = JDABuilder.createDefault("NzgzNTU4NDQzNTE5NzA1MDg4.X8cfqg._Rdt7ZMFLK_8rHWeVDqdjg5qyMQ").build();
		
		// Setting bot as idle
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		
		// Setting bot activity with setActivity(Activity.playing/watching/etc)
		jda.getPresence().setActivity(Activity.playing("VALORANT"));
		
		// Telling bot to listen to commands
		jda.addEventListener(new Commands());
	}
}
