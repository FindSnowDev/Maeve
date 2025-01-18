package net.findsnow;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.findsnow.commands.CommandManager;
import net.findsnow.listeners.EventListener;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MaeveBot {
	private final Dotenv config;
	private final ShardManager shardManager;
	private static final int ACTIVITY_CYCLE_UNITS = 30;
	private static final TimeUnit ACTIVITY_UPDATE_HANDLER = TimeUnit.MINUTES;

	public MaeveBot() throws LoginException {
		config = Dotenv.configure().load();
		String token = config.get("TOKEN");

		this.shardManager = createShardManager(token);

		initializeEventListeners();
		initializeActivityUpdater();
	}

	private ShardManager createShardManager(String token) {
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setActivity(getRandomActivity());

		builder.enableIntents(
				GatewayIntent.GUILD_MEMBERS,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.GUILD_PRESENCES);
		builder.setMemberCachePolicy(MemberCachePolicy.ONLINE);
		builder.setChunkingFilter(ChunkingFilter.ALL);

		builder.enableCache(
				CacheFlag.ONLINE_STATUS,
				CacheFlag.ACTIVITY,
				CacheFlag.CLIENT_STATUS);
		return builder.build();
	}

	private void initializeEventListeners() {
		shardManager.addEventListener(
				new EventListener(),
				new CommandManager()
		);
	}

	private void initializeActivityUpdater() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(
                this::cycleActivity,
                0,
                ACTIVITY_CYCLE_UNITS,
                ACTIVITY_UPDATE_HANDLER);
	}

	private Activity getRandomActivity() {
		List<Activity> activityList = List.of(
				// watching activities
				Activity.watching("My enemies.."),
				Activity.watching("My owner sleep"),
				Activity.watching("The birds outside"),
				Activity.watching("Hello Kitty"),
				Activity.watching("The Last of Us Season 1"),
				Activity.watching("Squid Games"),
				Activity.watching("The Mandalorian"),

				// playing activities
				Activity.playing("Valorant"),
				Activity.playing("Marvel Rivals"),
				Activity.playing("With butterflies"),
				Activity.playing("Minecraft"),
				Activity.playing("With my toys")
		);
		Random random = new Random();
		return activityList.get(random.nextInt(activityList.size()));
	}

	private void cycleActivity() {
		shardManager.setActivity(getRandomActivity());
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