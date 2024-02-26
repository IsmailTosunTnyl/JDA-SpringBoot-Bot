package net.ismailtosun.discordbotultimate.Services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Configurators.BotConfiguration;
import net.ismailtosun.discordbotultimate.Entity.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LiveDataService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final JDA jda;
    private  Guild guild ;
    private final PlayerManager playerManager;

    @Value("${spring.data.guild.id}")
    private String guildId;


    public LiveDataService(SimpMessageSendingOperations messagingTemplate, PlayerManager playerManager) {
        this.messagingTemplate = messagingTemplate;
        this.playerManager = playerManager;
        jda = BotConfiguration.jda;


        sendCurrentTrack();
    }

    public void sendCurrentTrack() {
        // wait until jda is ready

        while (jda == null || !jda.getStatus().equals(JDA.Status.CONNECTED)) {
            System.out.println("****Waiting for jda to be ready****");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // send current track to the client every 1 second if the track is not null
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    if (guild == null) {

                        Thread.sleep(2000);
                        guild = jda.getGuildById(guildId);
                        continue;
                    }
                    if (guild.getAudioManager().getConnectedChannel() != null && playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack() != null) {
                        Track track = new Track();
                        track.setTitle(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().title);
                        track.setAuthor(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().author);
                        track.setUrl(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().uri);
                        track.setDuration(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getDuration());
                        track.setPosition(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getPosition());

                        messagingTemplate.convertAndSend("/bot/currentSong", track);

                    }




                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }

        }).start();



    }




}
