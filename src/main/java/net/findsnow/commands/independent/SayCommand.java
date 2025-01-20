package net.findsnow.commands.independent;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class SayCommand implements ICommand{
	@Override
	public void execute(SlashCommandInteractionEvent event) {
		String message = event.getOption("message").getAsString();
		event.getChannel().sendMessage(message).queue();
		event.reply("Okay! I'm just a cat and don't understand words").setEphemeral(true).queue();
	}

	@Override
	public CommandData getCommandData() {
		return Commands.slash("say", "Make Maeve say something")
				.addOptions(new OptionData(OptionType.STRING, "message", "Go ahead and write something for me to say", true));
	}
}
