package net.ismailtosun.discordbotultimate.Controllers;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Track;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Value("${soundpad.base.dir}")
    private String soundPadBaseDir;
    @Value("${soundpad.volume}")
    private int soundPadVolume;
    @Value("${audioPlayer.volume}")
    private int audioPlayerVolume;
    public WebSocketController(PlayerManager playerManager,  JDA jda, SimpMessageSendingOperations messagingTemplate) {
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
            playerManager.loadAndPlay( guild  , playlistUrl, false,false);
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

    @MessageMapping("/bot/soundpad.play")
    public void playSoundPad(@Payload Map<String, String> message) throws InterruptedException {
        String soundPadID = message.get("soundpadId");
        Guild guild = jda.getGuildById(guildId);
        if (soundPadID != null) {
            try{

                String soundPadUrl = soundPadBaseDir + soundPadID;
                AudioTrack playingTrack = playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack();

                // increase the volume
                System.out.println("sound increased");
                playerManager.getGuildMusicManager(guild).audioPlayer.setVolume(soundPadVolume);
                if (playingTrack == null) {
                    playerManager.loadAndPlay(guild, soundPadUrl, false, false);

                }else {

                    // clone the track
                    AudioTrack playintrackClone = playingTrack.makeClone();
                    // set the position of the clone to the current position of the playing track
                    playintrackClone.setPosition(playingTrack.getPosition());

                    // add the previous track to top of the queue
                    playerManager.getGuildMusicManager(guild).scheduler.playNext(playintrackClone);

                    // load the sound
                    playerManager.loadAndPlay(guild, soundPadUrl, true, true);

                }
                // decrease the volume
                long trackLength =  playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getDuration()/1000;
                Thread.sleep(trackLength);
                playerManager.getGuildMusicManager(guild).audioPlayer.setVolume(audioPlayerVolume);
            }
            catch (Exception e) {
                // if the player only busy with soundpad, it will not decrease the volume, so we need to decrease it manually
                Thread.sleep(1000);
                playerManager.getGuildMusicManager(guild).audioPlayer.setVolume(audioPlayerVolume);
                e.printStackTrace();
            }






        } else {

            throw new IllegalArgumentException("Invalid message payload");
        }

    }






}
