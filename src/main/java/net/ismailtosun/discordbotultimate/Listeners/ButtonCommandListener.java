package net.ismailtosun.discordbotultimate.Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.ismailtosun.discordbotultimate.AudioPlayer.EmbedPlayer;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Constants.ButtonConstants;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.ismailtosun.discordbotultimate.Listeners.MediaCommandManager.getVoiceChannel;

public class ButtonCommandListener extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(MediaCommandManager.class);
    private final PlayerManager playerManager;
    private final PlaylistRepository playlistRepository;

    public ButtonCommandListener(PlayerManager playerManager, PlaylistRepository playlistRepository) {
        this.playerManager = playerManager;
        this.playlistRepository = playlistRepository;
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
        String buttonAction = event.getComponentId().split(ButtonConstants.SEPERATOR)[0];
        switch (buttonAction) {
            case ButtonConstants.PLAYLIST:
                handlePlaylistButton(event);
                break;
            case ButtonConstants.ACTION:
                handleActionButton(event);
                break;
            default:
                break;
        }
    }

    private void handleActionButton(ButtonInteractionEvent event) {
        String action = event.getComponentId().split(ButtonConstants.SEPERATOR)[1];
        switch (action) {
            case ButtonConstants.SHUFFLE:
                playerManager.getGuildMusicManager(event.getGuild()).scheduler.shuffleQueue();
                logger.info("Shuffled the queue");
                event.deferEdit().queue();
                break;
            case ButtonConstants.STOP:
                playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.stopTrack();
                event.reply("Stopped!").queue();
                break;
            case ButtonConstants.NEXT:
                playerManager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
                logger.info("Next track is playing");
                event.deferEdit().queue();
                break;

            default:
                break;
        }
    }

    private void handlePlaylistButton(ButtonInteractionEvent event) {
        String playlistName = event.getComponentId().split(ButtonConstants.SEPERATOR)[1];
        Playlist playlist = playlistRepository.findByName(event.getComponentId().split(ButtonConstants.SEPERATOR)[1]);
        String playlistURL = playlist.getURL();
        VoiceChannel channel = getVoiceChannel(event);
        if (channel == null) {
            return;
        }
        event.getGuild().getAudioManager().openAudioConnection(channel);
        playerManager.loadAndPlay(event.getGuild(), playlistURL, false, true);
        EmbedPlayer.getInstace().sendEmbed(event.getChannel().asTextChannel());
        event.deferEdit().queue();
    }
}
