package net.ismailtosun.discordbotultimate.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

public class GuildMusicManager {

    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    private final AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager, SimpMessageSendingOperations messagingTemplate) {
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer,messagingTemplate);
        this.audioPlayer.addListener(scheduler);
        this.sendHandler = new AudioPlayerSendHandler(audioPlayer);
        // set volume
        this.audioPlayer.setVolume(5);
    }
    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

}
