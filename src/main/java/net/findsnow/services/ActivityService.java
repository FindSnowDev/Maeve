package net.findsnow.services;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.findsnow.config.ActivityConfig;
import net.findsnow.database.Activities;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ActivityService {
	private final ShardManager shardManager;
	private final ScheduledExecutorService scheduler;

	public ActivityService(ShardManager shardManager) {
		this.shardManager = shardManager;
		this.scheduler = Executors.newScheduledThreadPool(1);
	}

	public void startActivityCycle() {
		scheduler.scheduleAtFixedRate(
				this::cycleActivity,
				0,
				ActivityConfig.UPDATE_INTERVAL,
				ActivityConfig.UPDATE_TIME_UNIT);
	}

	private void cycleActivity() {
		shardManager.setActivity(getRandomActivity());
	}

	private Activity getRandomActivity() {
		Random random = new Random();
		boolean isWatching = random.nextDouble() < 0.7;

		if (isWatching) {
			String watchingActivity = Activities.WATCHING_ACTIVITIES.get(
					random.nextInt(Activities.WATCHING_ACTIVITIES.size()));
			return Activity.watching(watchingActivity);
		} else {
			String playingActivity = Activities.PLAYING_ACTIVITIES.get(
					random.nextInt(Activities.PLAYING_ACTIVITIES.size()));
			return Activity.playing(playingActivity);
		}
	}
}
