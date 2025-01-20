package net.findsnow.listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.findsnow.database.IDProviders;
import net.findsnow.messages.SimpleMessages;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EventListener extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		User user = event.getUser();
		Random random = new Random();

		if (user.getId().equals(IDProviders.ELLE_ID)) {
			String message = SimpleMessages.ELLE_JOINING_MESSAGES.get(random.nextInt(SimpleMessages.ELLE_JOINING_MESSAGES.size()));
			Objects.requireNonNull(event.getGuild().getDefaultChannel()).asStandardGuildMessageChannel().sendMessage(message).queue();
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(IDProviders.LOVE_ROLE)).queue();
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(IDProviders.LOVE_ROLE_2)).queue();
		} else {
			String message = user.getAsMention() + "Welcome to the server, " + user.getAsTag() + "!";
			Objects.requireNonNull(event.getGuild().getDefaultChannel()).asStandardGuildMessageChannel().sendMessage(message).queue();
		}
	}

	@Override
	public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
		List<Member> members = event.getGuild().getMembers();
		int onlineMembers = 0;
		for (Member member : members) {
			if (member.getOnlineStatus()  == OnlineStatus.ONLINE) {
				onlineMembers++;
			}
		}

		User user = event.getUser();
		if (user.getId().equals(IDProviders.ELLE_ID)) {
            String message = user.getAsMention() + "YAYY ELLE IS ONLINE :D";
            Objects.requireNonNull(event.getGuild().getDefaultChannel()).asStandardGuildMessageChannel().sendMessage(message).queue();
        }
		String mesage = user.getAsTag() + " is now " + event.getNewOnlineStatus().getKey()  + "! There are now " + onlineMembers + " members online.";
		event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(mesage).queue();
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		User user = event.getUser();
		String emoji = event.getReaction().getEmoji().getAsReactionCode();
		String channelMention = event.getChannel().getAsMention();
		String jumpLink = event.getJumpUrl();

		String message = user.getAsTag() + " reacted with " + emoji + " in " + channelMention + " | " + jumpLink;
		Objects.requireNonNull(event.getGuild().getDefaultChannel()).asStandardGuildMessageChannel().sendMessage(message).queue();
	}
}
