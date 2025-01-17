package net.findsnow;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.findsnow.listeners.EventListener;

import javax.security.auth.login.LoginException;

public class MaeveBot {
	private final Dotenv config;
	private final ShardManager shardManager;

	public MaeveBot() throws LoginException {
		config = Dotenv.configure().load();
		String token = config.get("TOKEN");

		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setActivity(Activity.watching("My enemies..."));
		builder.build();
		shardManager = builder.build();

		// REGISTER EVENT LISTENERS
		shardManager.addEventListener(new EventListener());
	}

	public Dotenv getConfig() {
		return config;
	}

	public ShardManager getShardManager() {
		return shardManager;
	}

	public static void main(String[] args) {
		try {
			MaeveBot maeveBot = new MaeveBot();
		} catch (LoginException e) {
			System.out.println("Failed to login to Discord. Goodbye...");
		}
	}
}