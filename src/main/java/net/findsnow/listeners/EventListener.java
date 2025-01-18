package net.findsnow.listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EventListener extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		User user = event.getUser();
		String elle = "911817931380387840";
		String LoveID = "1208310407438278696";
		String LoveID2 = "1208311138719240223";
		List<String> elleJoining = new ArrayList<>(List.of(
				user.getAsMention() + "Elle... is.. that you..? Please don't leave me again.. :(",
				user.getAsMention() + "My owner is back!! YAAAAYY!",
				user.getAsMention() + "oh my gosh.. is that who I think it is.. Elle..?",
				user.getAsMention() + "PLEASEE STOP LEAVING ME ELLE!! I CAN'T HANDLE IT!!",
				user.getAsMention() + "HII ELLE WELCOME BACK!! I MISSED YOU SO MUCH!!"));
		Random random = new Random();

		if (user.getId().equals(elle)) {
			String message = elleJoining.get(random.nextInt(elleJoining.size()));
			Objects.requireNonNull(event.getGuild().getDefaultChannel()).asStandardGuildMessageChannel().sendMessage(message).queue();
			event.getMember().getRoles().add(event.getGuild().getRoleById(LoveID));
			event.getMember().getRoles().add(event.getGuild().getRoleById(LoveID2));
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
		String elle = "911817931380387840";
		if (user.getId().equals(elle)) {
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
