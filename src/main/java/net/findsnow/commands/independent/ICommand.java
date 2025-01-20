package net.findsnow.commands.independent;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface ICommand {
	void execute(SlashCommandInteractionEvent event);
	CommandData getCommandData();
}
