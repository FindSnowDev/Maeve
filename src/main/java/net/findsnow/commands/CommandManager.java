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

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

		String command = event.getName();
		if (command.equals("maeve")) {
			String userTag = event.getUser().getAsMention();
			event.reply("meow! " + userTag + " yes I am a cat!!").queue();
		}
		else if (command.equals("ping")) {
			String userTag = event.getUser().getAsMention();
			event.reply("ping! " + userTag).queue();
		}
		// roles
		else if (command.equals("roles")) {
			event.deferReply().setEphemeral(true).queue();
			String response = "";
			for (Role role : event.getGuild().getRoles()) {
				response += role.getAsMention() + "\n";
			}
			event.getHook().sendMessage(response).queue();
		}
		else if (command.equals("say")) {
			OptionMapping messageOption = event.getOption("message");
			String message = messageOption.getAsString();
			event.getChannel().sendMessage(message).queue();
			event.reply("okay! im a cat i dont know human words..").queue();
		}
	}

	// Guild Commands

	@Override
	public void onGuildReady(GuildReadyEvent event) {
		List<CommandData> commandData = new ArrayList<>();
		commandData.add(Commands.slash("maeve", "Maeve talks"));
		commandData.add(Commands.slash("ping", "Do it"));
		commandData.add(Commands.slash("roles", "Display Roles"));

		OptionData option1 = new OptionData(OptionType.STRING, "message", "Go ahead and write something for me to say", true);
		commandData.add(Commands.slash("say", "Make Maeve say something").addOptions(option1));

		event.getGuild().updateCommands().addCommands(commandData).queue();
	}
}
