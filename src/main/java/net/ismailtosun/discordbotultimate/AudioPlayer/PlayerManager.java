package net.ismailtosun.discordbotultimate.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Entity.Track;
import net.ismailtosun.discordbotultimate.Services.TrackQueueUpdateService;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
public class PlayerManager {

    public final Map<Long, GuildMusicManager> musicManagers;
    public final AudioPlayerManager audioPlayerManager;
    private final SimpMessageSendingOperations messagingTemplate;
    private final TrackQueueUpdateService trackQueueUpdateService;


    public PlayerManager(SimpMessageSendingOperations messagingTemplate) {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.messagingTemplate = messagingTemplate;
        this.trackQueueUpdateService = new TrackQueueUpdateService(messagingTemplate);

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);

    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
          return musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
                final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager, messagingTemplate);
                guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
                return guildMusicManager;
            });
    }

    public void loadAndPlay(TextChannel textChannel, String trackUrl,boolean playNext) {
        final GuildMusicManager musicManager = getGuildMusicManager(textChannel.getGuild());

        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {

                if(playNext){

                    musicManager.scheduler.playNext(audioTrack);
                }
                else {
                    musicManager.scheduler.queue(audioTrack);
                }
                trackQueueUpdateService.updateQueue(musicManager.scheduler.queue);


                textChannel.sendMessage("Adding to queue: " + audioTrack.getInfo().title).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> tracks = audioPlaylist.getTracks();

                if(!tracks.isEmpty()){
                    if (trackUrl.contains("list")) {
                        textChannel.sendMessage("Adding playlist to queue: " + audioPlaylist.getName()).queue();

                        for (AudioTrack track : tracks) {
                            if (playNext)
                                musicManager.scheduler.playNext(track);
                            else
                                musicManager.scheduler.queue(track);
                        }

                    }
                    else {
                       if (playNext)
                           musicManager.scheduler.playNext(tracks.get(0));
                       else
                           musicManager.scheduler.queue(tracks.get(0));

                    }
                    trackQueueUpdateService.updateQueue(musicManager.scheduler.queue);
                    textChannel.sendMessage("Adding to queue: " + tracks.get(0).getInfo().title).queue();

                }



            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });

    }

    public void loadAndPlay(Guild guild, String trackUrl,boolean playNext) {
        final GuildMusicManager musicManager = getGuildMusicManager(guild);


        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                if(playNext){
                    musicManager.scheduler.playNext(audioTrack);
                }
                else {
                    musicManager.scheduler.queue(audioTrack);
                }
                // For updating queue
                trackQueueUpdateService.updateQueue(musicManager.scheduler.queue);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> tracks = audioPlaylist.getTracks();


                System.out.println(tracks);
                if(!tracks.isEmpty()){
                    if (trackUrl.contains("list")) {

                        for (AudioTrack track : tracks) {
                            musicManager.scheduler.queue(track);
                        }

                    }
                    else {
                        musicManager.scheduler.queue(tracks.get(0));

                    }
                    trackQueueUpdateService.updateQueue(musicManager.scheduler.queue);

                }



            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });

    }





    public Playlist getplaylist(TextChannel textChannel, String trackUrl) throws InterruptedException {
        /*
        *
        * it's return a Playlist object with tracks and url
        * it's need playlist url and lavaplayer function to getting playlist info and tracks
        *
        *
        *
        * */




        final GuildMusicManager musicManager = getGuildMusicManager(textChannel.getGuild());
        Playlist playlist = new Playlist();
        ArrayList<Track> trackArrayList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1); // Create a latch to wait for completion

        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                // Handle individual track loaded (if needed)
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> tracks = audioPlaylist.getTracks();

                if (!tracks.isEmpty() && trackUrl.contains("list")) {
                    playlist.setName(audioPlaylist.getName());
                    for (AudioTrack track : tracks) {

                        trackArrayList.add(new Track(track.getInfo().uri, track.getInfo().title, track.getInfo().author, track.getDuration(), track.getPosition()));
                    }
                } else {
                    textChannel.sendMessage("Not a playlist").queue();
                }

                latch.countDown(); // Release the latch to signal completion
            }

            @Override
            public void noMatches() {
                latch.countDown(); // Release the latch to signal completion
            }

            @Override
            public void loadFailed(FriendlyException e) {
                latch.countDown(); // Release the latch to signal completion
            }
        });

        latch.await(); // Wait for the latch to be released
        playlist.setTracks(trackArrayList.toArray(new Track[0]));
        playlist.setURL(trackUrl);
        return playlist;
    }





}
