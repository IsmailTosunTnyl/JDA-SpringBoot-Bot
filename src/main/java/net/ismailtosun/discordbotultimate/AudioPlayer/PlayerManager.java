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
import net.ismailtosun.discordbotultimate.AudioPlayer.GuildMusicManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    public final Map<Long, GuildMusicManager> musicManagers;
    public final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);

    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
          return musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
                final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);
                guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
                return guildMusicManager;
            });
    }

    public void loadAndPlay(TextChannel textChannel, String trackUrl) {
        final GuildMusicManager musicManager = getGuildMusicManager(textChannel.getGuild());


        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);

                textChannel.sendMessage("Adding to queue: " + audioTrack.getInfo().title).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> tracks = audioPlaylist.getTracks();


                System.out.println(tracks);
                if(!tracks.isEmpty()){
                    if (trackUrl.contains("list")) {
                        textChannel.sendMessage("Adding playlist to queue: " + audioPlaylist.getName()).queue();
                        textChannel.sendMessage("Adding to queue: " + tracks.get(0).getInfo().title).queue();
                        for (AudioTrack track : tracks) {
                            musicManager.scheduler.queue(track);
                        }
                    }
                    else {
                        musicManager.scheduler.queue(tracks.get(0));
                        textChannel.sendMessage("Adding to queue: " + tracks.get(0).getInfo().title).queue();
                    }

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




}
