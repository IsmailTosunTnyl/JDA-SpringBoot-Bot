package net.ismailtosun.discordbotultimate.Listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCommands extends ListenerAdapter {
    private  PlaylistRepository playlistRepository;

    public PlaylistCommands(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("playlists")) {
           playlistRepository.findAll().forEach(playlist -> {
               event.reply(playlist.getName()).queue();
           });
        }

        if (event.getName().equals("addplaylist")) {
            String name = event.getOption("name").getAsString();
            String url = event.getOption("url").getAsString();
            playlistRepository.save(new Playlist(url, name, new String[0]));
            event.reply("Playlist added").queue();
        }
    }


    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDatas = new ArrayList<>();

        commandDatas.add(Commands.slash("addplaylist", "Adds a playlist")
                .addOption(OptionType.STRING, "name", "Name of the playlist", true)
                .addOption(OptionType.STRING, "url", "URL of the playlist", true));

        commandDatas.add(Commands.slash("playlists", "Shows all playlists"));

        event.getGuild().updateCommands().addCommands(commandDatas).queue();

    }
}
