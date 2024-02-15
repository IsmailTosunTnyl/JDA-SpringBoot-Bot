package net.ismailtosun.discordbotultimate.Controllers;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import net.dv8tion.jda.api.JDA;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(maxAge = 3600, origins = "*")
public class WebSocketController {

    private PlayerManager playerManager;
    private JDA jda;

    private final SimpMessageSendingOperations messagingTemplate;

    @Value("${spring.data.guild.id}")
    private String guildId;
    public WebSocketController(PlayerManager playerManager, JDA jda, SimpMessageSendingOperations messagingTemplate) {
        this.playerManager = playerManager;
        this.jda = jda;
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/bot/song.changeById")
    public void playTrack(@Payload Map<String, String> message, SimpMessageHeaderAccessor headerAccessor) {
        String trackId = message.get("trackId");
        if (trackId != null) {

            Guild guild = jda.getGuildById(guildId);
            // play track in queue which has the same position as trackId
            playerManager.getGuildMusicManager(guild).scheduler.playTrackById(Integer.parseInt(trackId)+1);


        } else {
            // Handle invalid message, log or return an error response

        }
    }

    @MessageMapping("/bot/song.changeNext")
    public void playNextTrack() {
        Guild guild = jda.getGuildById(guildId);
        playerManager.getGuildMusicManager(guild).scheduler.nextTrack();

    }


    @MessageMapping("/bot/song.seekPosition")
    public void seekPosition(@Payload Map<String, String> message, SimpMessageHeaderAccessor headerAccessor) {
        String position = message.get("position");
        if (position != null) {

            Guild guild = jda.getGuildById(guildId);
            // play track in queue which has the same position as trackId
            playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().setPosition(Long.parseLong(position));

        } else {
            throw new IllegalArgumentException("Invalid message payload");
        }
    }

    @MessageMapping("/bot/song.playPause")
    public boolean playPause() {
        Guild guild = jda.getGuildById(guildId);
        if (playerManager.getGuildMusicManager(guild).audioPlayer.isPaused()) {
            playerManager.getGuildMusicManager(guild).audioPlayer.setPaused(false);
            return true;
        } else {
            playerManager.getGuildMusicManager(guild).audioPlayer.setPaused(true);
            return false;
        }

    }

    @MessageMapping("/bot/playlist.play")
    public void playPlaylist(@Payload Map<String, String> message, SimpMessageHeaderAccessor headerAccessor) {
        String playlistUrl = message.get("playlistUrl");

        if (playlistUrl != null) {
            Guild guild = jda.getGuildById(guildId);
            playerManager.loadAndPlay( guild  , playlistUrl, false);
        } else {
            throw new IllegalArgumentException("Invalid message payload");
        }

    }

    @MessageMapping("/bot/playlist.clear")
    public void clearPlaylist() {
        Guild guild = jda.getGuildById(guildId);
        playerManager.getGuildMusicManager(guild).scheduler.queue.clear();
        playerManager.getGuildMusicManager(guild).audioPlayer.stopTrack();
    }

    @MessageMapping("/bot/playlist.shuffle")
    public void shufflePlaylist() {
        Guild guild = jda.getGuildById(guildId);
        playerManager.musicManagers.get(guild.getIdLong()).scheduler.shuffleQueue();

        List<Track> trackList = new ArrayList<>();

        if (!playerManager.getGuildMusicManager(guild).scheduler.queue.isEmpty()) {
            for (AudioTrack audioTrack : playerManager.getGuildMusicManager(guild).scheduler.queue) {

                trackList.add(new Track(audioTrack.getInfo().uri,
                        audioTrack.getInfo().title,
                        audioTrack.getInfo().author,
                        audioTrack.getDuration(),
                        audioTrack.getPosition()));
            }

        }
        System.out.println("Shuffled queue: " + trackList);
        messagingTemplate.convertAndSend("/bot/playlist", trackList);


    }






}
