package net.findsnow.services;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public final class ShardManagerService {
	public static ShardManager createShardManager(String token) {
		return DefaultShardManagerBuilder.createDefault(token)
				.setStatus(OnlineStatus.ONLINE)
				.enableIntents(
						GatewayIntent.GUILD_MEMBERS,
						GatewayIntent.GUILD_MESSAGES,
						GatewayIntent.GUILD_PRESENCES)
				.setMemberCachePolicy(MemberCachePolicy.ALL)
				.setChunkingFilter(ChunkingFilter.ALL)
				.enableCache(
						CacheFlag.ONLINE_STATUS,
						CacheFlag.ACTIVITY,
						CacheFlag.CLIENT_STATUS)
				.build();
	}
}
