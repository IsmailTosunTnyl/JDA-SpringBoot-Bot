package net.ismailtosun.discordbotultimate.Controllers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Configurators.BotConfiguration;
import net.ismailtosun.discordbotultimate.Entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LiveDataController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final JDA jda;
    private  Guild guild ;
    private final PlayerManager playerManager;



    public LiveDataController(SimpMessageSendingOperations messagingTemplate, PlayerManager playerManager) {
        this.messagingTemplate = messagingTemplate;
        this.playerManager = playerManager;
        jda = BotConfiguration.jda;
        System.out.println(jda);
        guild = jda.getGuildById("775351095748198442");

        sendCurrentTrack();
    }

    public void sendCurrentTrack() {
        // get current track


        // send current track to the client every 1 second if the track is not null
        new Thread(() -> {
            while (true) {
                try {
                    if (guild == null) {
                        System.out.println("guild is null");
                        Thread.sleep(1000);
                        guild = jda.getGuildById("775351095748198442");
                        continue;
                    }
                    Thread.sleep(1000);
                    if (guild.getAudioManager().getConnectedChannel() != null && playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack() != null) {
                        Track track = new Track();
                        track.setTitle(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().title);
                        track.setAuthor(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().author);
                        track.setUrl(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().uri);
                        track.setDuration(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getDuration());
                        track.setPosition(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getPosition());
                        System.out.println(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getDuration());
                        System.out.println(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getPosition());
                        messagingTemplate.convertAndSend("/bot/currentSong", track);
                        messagingTemplate.convertAndSend("/bot/next", "HEHE");

                    }




                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();



    }


}
