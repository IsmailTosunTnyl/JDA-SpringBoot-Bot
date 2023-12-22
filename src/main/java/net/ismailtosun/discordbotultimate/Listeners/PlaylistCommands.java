package net.ismailtosun.discordbotultimate.Listeners;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaylistCommands extends ListenerAdapter {
    private  PlaylistRepository playlistRepository;
    private PlayerManager playerManager;
    public PlaylistCommands(PlaylistRepository playlistRepository, PlayerManager playerManager) {
        this.playlistRepository = playlistRepository;
        this.playerManager = playerManager;

    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("playlists")) {
           playlistRepository.findAll().forEach(playlist -> {
               event.getChannel().asTextChannel().sendMessage(playlist.getName()).queue();
           });
        }

        if (event.getName().equals("addplaylist")) {

            String url = event.getOption("url").getAsString();

            Playlist existingPlaylist = playlistRepository.findById(url).orElse(null);

            if (existingPlaylist != null) {
                event.reply("BU VAR VAR BI BAK ATMADAN ŞUNU BU VAR").queue();
                return;
            }


           Playlist playlist = playerManager.getplaylist(event.getChannel().asTextChannel(), url);

            playlistRepository.save(playlist);
            event.reply("Playlist added").queue();
        }
    }


    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDatas = new ArrayList<>();

        commandDatas.add(Commands.slash("addplaylist", "Adds a playlist")

                .addOption(OptionType.STRING, "url", "URL of the playlist", true));

        commandDatas.add(Commands.slash("playlists", "Shows all playlists"));

        event.getGuild().updateCommands().addCommands(commandDatas).queue();

    }
}
