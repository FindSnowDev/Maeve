package net.findsnow.commands.independent;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class PingCommand implements ICommand{
	@Override
	public void execute(SlashCommandInteractionEvent event) {
		String userTag = event.getUser().getAsMention();
		event.reply("pong!" + userTag).queue();
	}

	@Override
	public CommandData getCommandData() {
		return Commands.slash("ping", "Check Maeve's response time!");
	}
}
