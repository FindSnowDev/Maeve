package net.findsnow;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.findsnow.commands.CommandManager;
import net.findsnow.listeners.EventListener;
import net.findsnow.services.ActivityService;
import net.findsnow.services.ShardManagerService;

import javax.security.auth.login.LoginException;


public class MaeveBot {
	private final Dotenv config;
	private final ShardManager shardManager;
	private final ActivityService activityService;

	public MaeveBot() throws LoginException {
		this.config = Dotenv.configure().load();
		this.shardManager = ShardManagerService.createShardManager(config.get("TOKEN"));
		this.activityService = new ActivityService(shardManager);

		initializeEventListeners();
		activityService.startActivityCycle();
	}

	private void initializeEventListeners() {
		shardManager.addEventListener(
				new EventListener(),
				new CommandManager()
		);
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