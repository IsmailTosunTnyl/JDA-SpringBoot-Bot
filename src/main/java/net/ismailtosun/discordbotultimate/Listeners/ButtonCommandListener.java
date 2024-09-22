package net.ismailtosun.discordbotultimate.Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
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
                event.reply("Queue shuffled!").queue();
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
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        int songCount = playerManager.getGuildMusicManager(event.getGuild())
                .scheduler.queue.size() + playlist.getTracks().length;
        messageCreateBuilder.setEmbeds(new EmbedBuilder().setTitle(playlistName).setFooter("Added to queue")
                .setDescription("Total songs in the queue: " + songCount).build());
        messageCreateBuilder.addActionRow(Button.primary(ButtonConstants.ACTION + ButtonConstants.SEPERATOR
                + ButtonConstants.SHUFFLE, "Shuffle").withEmoji(Emoji.fromUnicode("🔀")));
        event.reply(messageCreateBuilder.build()).queue();
    }
}
