package net.findsnow.listeners;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class EventListener extends ListenerAdapter {
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		User user = event.getUser();
		String emoji = event.getReaction().getEmoji().getAsReactionCode();
		String channelMention = event.getChannel().getAsMention();
		String jumpLink = event.getJumpUrl();

		String message = user.getAsTag() + "reacted with " + emoji + " in " + channelMention + " | " + jumpLink;
		Objects.requireNonNull(event.getGuild().getDefaultChannel()).asStandardGuildMessageChannel().sendMessage(message).queue();
	}
}
