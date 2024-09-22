package net.ismailtosun.discordbotultimate.Listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SlashCommands extends ListenerAdapter {
    Logger logger = LoggerFactory.getLogger(SlashCommands.class);

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        super.onGuildReady(event);
        List<CommandData> commands = new ArrayList<>();

        commands.add(Commands.slash("ping", "Pong!"));
        commands.add(Commands.slash("join", "Join Audio Channel"));
        commands.add(Commands.slash("play", "Play a song")
                .addOption(OptionType.STRING, "song", "Song name or url", true));
        commands.add(Commands.slash("playnext", "Play the song next in queue")
                .addOption(OptionType.STRING, "song", "Song name or url", true));
        commands.add(Commands.slash("next", "Play next song"));
        commands.add(Commands.slash("now", "Show current song"));
        commands.add(Commands.slash("shuffle", "Shuffle the queue"));
        commands.add(Commands.slash("playlist", "Show the playlists"));
        commands.add(Commands.slash("url", "Get the Web UI URL"));
        commands.add(Commands.slash("delete", "Delete message from channel")
                .addOption(OptionType.INTEGER, "messagecount", "Number of messages to delete", true));
        commands.add(Commands.slash("soundpadupload", "Admin usage only please, Its upload the audio folder to mongo db"));
        commands.add(Commands.slash("soundpaddownload", "Admin usage only please, Its download the audio folder from mongo db"));
        commands.add(Commands.slash("player", "Media player controls"));
        event.getGuild().updateCommands().addCommands(commands).queue();
        logger.info("Commands are added to the guild");
    }
}
