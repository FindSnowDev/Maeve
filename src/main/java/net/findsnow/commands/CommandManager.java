package net.findsnow.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.findsnow.commands.independent.ICommand;
import net.findsnow.commands.independent.MaeveCommand;
import net.findsnow.commands.independent.PingCommand;
import net.findsnow.commands.independent.SayCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager extends ListenerAdapter {
	private final Map<String, ICommand> commands = new HashMap<>();

	public CommandManager() {
		addCommand(new MaeveCommand());
		addCommand(new PingCommand());
		addCommand(new SayCommand());
	}

	private void addCommand(ICommand command) {
		commands.put(command.getCommandData().getName(), command);
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		String commandName = event.getName();
		ICommand command = commands.get(commandName);
		if (command != null) {
			command.execute(event);
		}
	}

	@Override
	public void onGuildReady(GuildReadyEvent event) {
		List<CommandData> commandData = new ArrayList<>();
		for (ICommand command : commands.values()) {
			commandData.add(command.getCommandData());
		}
		event.getGuild().updateCommands().addCommands(commandData).queue();
	}
}
