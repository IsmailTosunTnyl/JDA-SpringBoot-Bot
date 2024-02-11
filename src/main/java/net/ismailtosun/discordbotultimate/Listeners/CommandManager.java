package net.ismailtosun.discordbotultimate.Listeners;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class CommandManager extends ListenerAdapter {


    private final PlayerManager playerManager;
    private final SimpMessageSendingOperations messagingTemplate;

    private final PlaylistRepository playlistRepository;


    public CommandManager(PlayerManager playerManager, SimpMessageSendingOperations messagingTemplate, PlaylistRepository playlistRepository) {
        this.playerManager = playerManager;
        this.messagingTemplate = messagingTemplate;
        this.playlistRepository = playlistRepository;

    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        if (event.getName().equals("ping")) {
            event.reply("Pong!").queue();
        } else if (event.getName().equals("join")) {
            // get the user's voice channel
            VoiceChannel channel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
            // join the voice channel
            event.getGuild().getAudioManager().openAudioConnection(channel);
            event.reply("Joined " + channel.getName()).queue();

            System.out.println(playerManager);
        } else if (event.getName().equals("play")) {
            // get the user's voice channel
            VoiceChannel channel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
            // get song name or url
            String song = event.getOption("song").getAsString();
            System.out.println(song);


            // join the voice channel
            event.getGuild().getAudioManager().openAudioConnection(channel);
            //event.reply("Joined " + channel.getName()).queue();

            // play the song
            playerManager.loadAndPlay(event.getChannel().asTextChannel(), getURI(song));

            // if the song is a playlist add the playlist to db
            Playlist existingPlaylist = playlistRepository.findById(song).orElse(null);
            System.out.printf("existingPlaylist: %s", existingPlaylist);
            if (existingPlaylist == null) {
                existingPlaylist = playerManager.getplaylist(event.getChannel().asTextChannel(), song);

                playlistRepository.insert(existingPlaylist);

                event.getChannel().asTextChannel().sendMessage(" NEW Playlist added " + existingPlaylist.getURL()).queue();

            }

        } else if (event.getName().equals("next")) {
            // get the user's voice channel
            Boolean channel = event.getMember().getVoiceState().inAudioChannel();
            // check player has joined a voice channel
            if (!channel) {
                event.reply("You must join a voice channel first!").queue();
                return;
            }
            // check player is in the same voice channel as the bot
            if (!event.getMember().getVoiceState().getChannel().equals(event.getGuild().getAudioManager().getConnectedChannel())) {
                event.reply("You must be in the same voice channel as the bot!").queue();
                return;
            }
            // check if the bot is playing a song
            if (playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack() == null) {
                event.reply("The bot is not playing a song!").queue();
                return;
            }


            // play the song
            playerManager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
            //send message with new song name
            event.reply("Playing next song: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title).queue();
        } else if (event.getName().equals("now")) {

            event.reply("Playing now: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title).queue();
        }
        else if (event.getName().equals("test")) {
            String test = event.getOption("test").getAsString();
            messagingTemplate.convertAndSend("/topic/public", test);
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        super.onGuildReady(event);
        List<CommandData> commands = new ArrayList<>();

        commands.add(Commands.slash("ping", "Pong!"));
        commands.add(Commands.slash("join", "Join Audio Channel"));
        commands.add(Commands.slash("play", "Play a song")
                .addOption(OptionType.STRING, "song", "Song name or url", true));
        commands.add(Commands.slash("next", "Play next song"));
        commands.add(Commands.slash("now", "Show current song"));
        commands.add(Commands.slash("test", "test").addOption(OptionType.STRING, "test", "test", false));
        event.getGuild().updateCommands().addCommands(commands).queue();

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
