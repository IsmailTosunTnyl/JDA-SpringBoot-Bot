package net.ismailtosun.discordbotultimate.Listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.ArrayList;
import java.util.List;

public class Commands extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        super.onGuildReady(event);
        List<CommandData> commands = new ArrayList<>();

        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("ping", "Pong!"));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("join", "Join Audio Channel"));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("play", "Play a song")
                .addOption(OptionType.STRING, "song", "Song name or url", true));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("next", "Play next song"));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("now", "Show current song"));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("url","Get the Web UI URL"));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("shuffle","Shuffle the queue"));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("playnext","Play the song next in queue")
                .addOption(OptionType.STRING, "song", "Song name or url", true));
        commands.add(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("delete","Delete message from channel")
                .addOption(OptionType.INTEGER,"messagecount","Number of messages to delete",true));

        System.out.println("MediaCommandManager.onGuildReady");
        event.getGuild().updateCommands().addCommands(commands).queue();
    }
}
