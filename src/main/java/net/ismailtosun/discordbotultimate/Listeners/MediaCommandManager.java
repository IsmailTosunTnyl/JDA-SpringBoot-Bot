package net.ismailtosun.discordbotultimate.Listeners;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.ismailtosun.discordbotultimate.AudioPlayer.EmbedPlayer;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Constants.ButtonConstants;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import net.ismailtosun.discordbotultimate.Services.TokenService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.ArrayList;
import java.util.List;


public class MediaCommandManager extends ListenerAdapter {

    static Logger logger = LoggerFactory.getLogger(MediaCommandManager.class);
    private final PlayerManager playerManager;
    private final SimpMessageSendingOperations messagingTemplate;

    private final PlaylistRepository playlistRepository;
    private final TokenService tokenService;


    public MediaCommandManager(PlayerManager playerManager, SimpMessageSendingOperations messagingTemplate, PlaylistRepository playlistRepository, TokenService tokenService) {
        this.playerManager = playerManager;
        this.messagingTemplate = messagingTemplate;
        this.playlistRepository = playlistRepository;
        this.tokenService = tokenService;

    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()) {
            case "ping":
                handlePingCommand(event);
                logger.info("Ping command received");
                break;
            case "join":
                handleJoinCommand(event);
                logger.info("Join command received");
                break;
            case "play":
                handlePlayCommand(event, false);
                logger.info("Play command received");
                break;
            case "next":
                handleNextCommand(event);
                logger.info("Next command received");
                break;
            case "now":
                handleNowCommand(event);
                logger.info("Now command received");
                break;
            case "url":
                handleURLCommand(event);
                logger.info("URL command received");
                break;
            case "shuffle":
                handleShuffleCommand(event);
                logger.info("Shuffle command received");
                break;
            case "playnext":
                handlePlayCommand(event, true);
                logger.info("PlayNext command received");
                break;
            case "playlist":
                handlePlaylistCommand(event);
                logger.info("Playlist command received");
                break;
            case "player":
                handlePlayerCommand(event);
                logger.info("Player command received");
                break;

            default:
                break;
        }

    }

    private void handlePlayerCommand(SlashCommandInteractionEvent event) {
        EmbedPlayer embedPlayer = EmbedPlayer.getInstace();
        embedPlayer.setPlayerManager(playerManager);
        embedPlayer.setTextChannel(event.getChannel().asTextChannel());
           MessageEmbed embed = embedPlayer.createEmbed(playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack());
            // set the last message id to the current message id
        logger.info("Embed created");
        EmbedPlayer.getInstace().sendEmbed(event.getChannel().asTextChannel());

    }

    private void handlePlaylistCommand(SlashCommandInteractionEvent event) {
        List<Playlist> playlists = playlistRepository.findAll();
        List<Button> buttons = new ArrayList<>();

        for (Playlist playlist : playlists) {
            buttons.add(Button.primary(ButtonConstants.PLAYLIST + ButtonConstants.SEPERATOR + playlist.getName(), playlist.getName()));
        }

        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.setContent("Select a playlist to play:");
        // reorder the buttons using name as the key
        buttons.sort((b1, b2) -> b1.getLabel().compareTo(b2.getLabel()));
        // Add the buttons to the message group by 5
        // This is because a single action row can only have 5 buttons
        for (int i = 0; i < buttons.size(); i += 5) {
            List<Button> buttonGroup = buttons.subList(i, Math.min(i + 5, buttons.size()));
            messageCreateBuilder.addActionRow(buttonGroup);
        }
        // Send the message with the buttons
        event.reply(messageCreateBuilder.build()).queue();

    }


    private void handleShuffleCommand(SlashCommandInteractionEvent event) {
        playerManager.getGuildMusicManager(event.getGuild()).scheduler.shuffleQueue();
        event.reply("Queue shuffled!").queue();
    }

    public static VoiceChannel getVoiceChannel(GenericInteractionCreateEvent event) {
        AudioChannelUnion audioChannelUnion = event.getMember().getVoiceState().getChannel();
        // check if the user is in a voice channel
        if (audioChannelUnion == null) {
            TextChannel channel = event.getJDA().getTextChannelById(event.getChannel().getId());
            channel.sendMessage("You need to be in a voice channel to use this command!").queue();
            logger.warn("User is not in a voice channel");
            return null;
        }
        return audioChannelUnion.asVoiceChannel();
    }

    private void handleURLCommand(SlashCommandInteractionEvent event) {
        String base_url = "http://16.171.136.126:3131/";
        //String base_url = "http://localhost:8081/";
        String link = base_url + "?token=" + tokenService.generateToken();
        logger.info("Base URL: " + link);
        event.reply("")
                .addActionRow(
                        Button.link(link, Emoji.fromFormatted("<:yusuf:703694580335378474>"))
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
            logger.warn("The bot is not playing a song!");
            return;
        }
        event.reply("Playing now: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title).queue();

    }

    private void handleNextCommand(SlashCommandInteractionEvent event) {

        VoiceChannel channel = getVoiceChannel(event);
        if (channel == null) {
            logger.warn("User is not in a voice channel");
            return;
        }
        if (playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack() == null) {
            event.reply("The bot is not playing a song!").queue();
            logger.warn("The bot is not playing a song!");
            return;
        }
        playerManager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
        event.reply("Playing next song: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title).queue();
        logger.info("Playing next song: " + playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack().getInfo().title);


    }

    private void handlePlayCommand(SlashCommandInteractionEvent event, boolean playNext) throws InterruptedException {
        VoiceChannel channel = getVoiceChannel(event);
        if (channel == null) {
            logger.warn("User is not in a voice channel");
            return;
        }
        String song = event.getOption("song").getAsString();
        event.getGuild().getAudioManager().openAudioConnection(channel);
        playerManager.loadAndPlay(event.getGuild(), getURI(song), playNext, false);
        if (song.contains("playlist")) {
            logger.info("Playlist URL detected");
            Playlist existingPlaylist = playlistRepository.findById(song).orElse(playlistRepository.findByName(playerManager.getplaylist(event.getChannel().asTextChannel(), song).getName()));
            if (existingPlaylist == null) {
                existingPlaylist = playerManager.getplaylist(event.getChannel().asTextChannel(), song);
                playlistRepository.insert(existingPlaylist);
                event.getChannel().asTextChannel().sendMessage(" NEW Playlist added " + existingPlaylist.getURL()).queue();
                logger.info("New playlist added: " + existingPlaylist.getURL());
            }
        }
        EmbedPlayer.getInstace().sendEmbed(event.getChannel().asTextChannel());
        event.reply("Searching: " + song).queue();

        logger.info("Searching: " + song);

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
        event.getChannel().asTextChannel().sendMessage("Joined " + channel.getName()).queue();

        // send the url for the bot UI
        this.handleURLCommand(event);

    }

    private void handlePingCommand(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }


    public String getURI(String trackUrl) {
        if (trackUrl.contains("http")) {
            return trackUrl;
        } else {
            return "ytsearch:" + trackUrl + " Official Audio";
        }
    }
}
