package net.ismailtosun.discordbotultimate.Listeners;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import java.util.ArrayList;
import java.util.List;


public class MediaCommandManager extends ListenerAdapter {


    private final PlayerManager playerManager;
    private final SimpMessageSendingOperations messagingTemplate;

    private final PlaylistRepository playlistRepository;


    public MediaCommandManager(PlayerManager playerManager, SimpMessageSendingOperations messagingTemplate, PlaylistRepository playlistRepository) {
        this.playerManager = playerManager;
        this.messagingTemplate = messagingTemplate;
        this.playlistRepository = playlistRepository;

    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()) {
            case "ping":
                handlePingCommand(event);
                break;
            case "join":
                handleJoinCommand(event);
                break;
            case "play":
                handlePlayCommand(event,false);
                break;
            case "next":
                handleNextCommand(event);
                break;
            case "now":
                handleNowCommand(event);
                break;
            case "url":
                handleURLCommand(event);
                break;
            case "shuffle":
                handleShuffleCommand(event);
                break;
            case "playnext":
                handlePlayCommand(event,true);
                break;
            default:
                break;
        }

    }




    private void handleShuffleCommand(SlashCommandInteractionEvent event) {
        playerManager.getGuildMusicManager(event.getGuild()).scheduler.shuffleQueue();
        event.reply("Queue shuffled!").queue();
    }

    private VoiceChannel getVoiceChannel(SlashCommandInteractionEvent event) {
        AudioChannelUnion audioChannelUnion = event.getMember().getVoiceState().getChannel();
        // check if the user is in a voice channel
        if (audioChannelUnion == null) {
            event.reply("You must join a voice channel first!").queue();
            return null;
        }
        return audioChannelUnion.asVoiceChannel();
    }

    private void handleURLCommand(SlashCommandInteractionEvent event) {
        String link = "http://ismailtosun.net:3131/";

        event.reply("")
                .addActionRow(
                        Button.link(link, Emoji.fromFormatted("<:yusuf:703694580335378474>"))// Button with only a label
                ) // Button with only an emoji
                .queue();
    }

    private void handleNowCommand(SlashCommandInteractionEvent event) {

        VoiceChannel channel = getVoiceChannel(event);
        if (channel == null) {
            return;
        }
        if (playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack() == null) {
            event.reply("The bot is not playing a song!").queue();
            return;
        }
        event.reply("Playing now: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title).queue();
        
    }

    private void handleNextCommand(SlashCommandInteractionEvent event) {

        VoiceChannel channel = getVoiceChannel(event);
        if (channel == null) {
            return;
        }
        if (playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack() == null) {
            event.reply("The bot is not playing a song!").queue();
            return;
        }
        playerManager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
        event.reply("Playing next song: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title).queue();


        
    }

    private void handlePlayCommand(SlashCommandInteractionEvent event,boolean playNext) throws InterruptedException {
        VoiceChannel channel = getVoiceChannel(event);
        if (channel == null) {
            return;
        }
        String song = event.getOption("song").getAsString();
        event.getGuild().getAudioManager().openAudioConnection(channel);
        playerManager.loadAndPlay(event.getChannel().asTextChannel(), getURI(song),playNext);
        if (song.contains("playlist")) {
            Playlist existingPlaylist = playlistRepository.findById(song).orElse(playlistRepository.findByName(playerManager.getplaylist(event.getChannel().asTextChannel(), song).getName()));
            if (existingPlaylist == null) {
                existingPlaylist = playerManager.getplaylist(event.getChannel().asTextChannel(), song);
                playlistRepository.insert(existingPlaylist);
                event.getChannel().asTextChannel().sendMessage(" NEW Playlist added " + existingPlaylist.getURL()).queue();
            }
        }
        event.reply("Searching: " + song).queue();

    }

    private void handleJoinCommand(SlashCommandInteractionEvent event) {
        // get the user's voice channel
        VoiceChannel channel = getVoiceChannel(event);

        // check if the user is in a voice channel
        if (channel == null) {
            return;
        }

        // join the voice channel
        event.getGuild().getAudioManager().openAudioConnection(channel);
        event.reply("Joined " + channel.getName()).queue();
        
    }

    private void handlePingCommand(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }



    public  String getURI(String trackUrl) {
        if (trackUrl.contains("http")) {
            return trackUrl;
        }
        else {
            return "ytsearch:" + trackUrl +"Official Audio";
        }
    }
}
